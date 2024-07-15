package com.mashreq.crb.service;

import com.mashreq.crb.dto.ConferenceRoomBookingRequest;
import com.mashreq.crb.dto.ConferenceRoomBookingResponse;
import com.mashreq.crb.entity.ConferenceRoom;
import com.mashreq.crb.entity.ConferenceRoomBooking;
import com.mashreq.crb.entity.RoomMaintenance;
import com.mashreq.crb.exception.ConferenceRoomException;
import com.mashreq.crb.mapper.ConferenceRoomBookingMapper;
import com.mashreq.crb.repository.ConferenceRoomBookingRepository;
import com.mashreq.crb.repository.ConferenceRoomRepository;
import com.mashreq.crb.validator.BookingValidatorChain;
import jakarta.persistence.OptimisticLockException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ConferenceRoomBookingService {
    private static final Logger log = LoggerFactory.getLogger(ConferenceRoomBookingService.class);
    ConferenceRoomBookingRepository bookingRepository;
    ConferenceRoomRepository roomRepository;
    ConferenceRoomBookingMapper bookingMapper;
    BookingValidatorChain bookingValidatorChain;

    @Transactional
    public ConferenceRoomBookingResponse book(ConferenceRoomBookingRequest bookingRequest) {
        try {
                bookingValidatorChain.validate(bookingRequest);
                val attendees = bookingRequest.getAttendees();
                val availableRooms = roomRepository.findByCapacityGreaterThanEqualAndIsBookedFalse(attendees);
                if (availableRooms.isEmpty()) {
                    throw new ConferenceRoomException("No suitable conference room found for " + attendees + " attendees.");
                }
                val endTime = bookingRequest.getEndTime().toLocalTime();
                val startTime = bookingRequest.getStartTime().toLocalTime();

                for (ConferenceRoom eligibleRoom : availableRooms) {
                    try {
                        if (!isBookingTimeOverlapsMaintenance(eligibleRoom, startTime, endTime)) {
                            eligibleRoom.setBooked(true);
                            roomRepository.saveAndFlush(eligibleRoom);
                            ConferenceRoomBooking conferenceRoomBooking = bookingMapper.mapToEntity(bookingRequest);
                            if (conferenceRoomBooking == null) {
                                conferenceRoomBooking = new ConferenceRoomBooking();
                            }
                            conferenceRoomBooking.setId(eligibleRoom.getId());
                            conferenceRoomBooking.setRoom(eligibleRoom);
                            return bookingMapper.mapToDto(bookingRepository.save(conferenceRoomBooking));
                        }
                    } catch (OptimisticLockException exception) {
                        log.info("Concurrent booking attempt detected. Trying next available room.");
                    }
                }
                throw new ConferenceRoomException("Unable to find a suitable conference room for " + attendees + " attendees. " +
                        "Booking cannot be done during maintenance time");

        } catch (Exception e) {
            log.error("Unexpected error during room booking: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error during room booking", e);
        }
    }
    public ConferenceRoomBookingResponse findById(Long id) {
        try {
            Optional<ConferenceRoomBooking> bookingOptional = bookingRepository.findById(id);
            if (bookingOptional == null) {
                bookingOptional = Optional.empty();
            }
            return bookingOptional
                    .map(bookingMapper::mapToDto)
                    .orElseThrow(() -> new ConferenceRoomException("Unable to find a booking for id: " + id));
        } catch (Exception e) {
            log.error("Unexpected error during booking retrieval: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error during booking retrieval", e);
        }
    }

    @Transactional
    public ConferenceRoomBookingResponse delete(Long id) {
        try {
            log.info("Attempting to delete a booking with id: {}", id);
            return bookingRepository.findById(id)
                    .map(conferenceRoomBooking -> {
                        log.debug("Soft deleting the booking having id: {}", conferenceRoomBooking.getId());
                        conferenceRoomBooking.getRoom().setBooked(false);
                        roomRepository.save(conferenceRoomBooking.getRoom());
                        conferenceRoomBooking.setRemoved(true);
                        conferenceRoomBooking.setRoomReleased(true);
                        return bookingMapper.mapToDto(bookingRepository.save(conferenceRoomBooking));
                    })
                    .orElseThrow(() -> new ConferenceRoomException("Unable to find a booking for id: " + id));
        } catch (Exception e) {
            log.error("Unexpected error during booking deletion: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error during booking deletion", e);
        }
    }

    public List<ConferenceRoomBookingResponse> findAll() {
        try {
            log.info("Attempting to find all bookings");
            return bookingRepository.findAll()
                    .stream()
                    .map(bookingMapper::mapToDto)
                    .toList();
        } catch (Exception e) {
            log.error("Unexpected error during retrieval of all bookings: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error during retrieval of all bookings", e);
        }
    }

    private boolean isBookingTimeOverlapsMaintenance(ConferenceRoom eligibleRoom, LocalTime start, LocalTime end) {
        return eligibleRoom.getRoomMaintenanceWindow()
                .stream()
                .anyMatch(roomMaintenance -> isOverlapping(roomMaintenance, start, end));
    }

    private boolean isOverlapping(RoomMaintenance roomMaintenance, LocalTime start, LocalTime end) {
        return roomMaintenance.getStartTime().isBefore(end) && roomMaintenance.getEndTime().isAfter(start);
    }
}
