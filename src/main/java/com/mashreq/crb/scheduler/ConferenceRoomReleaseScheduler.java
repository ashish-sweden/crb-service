package com.mashreq.crb.scheduler;

import com.mashreq.crb.repository.ConferenceRoomBookingRepository;
import com.mashreq.crb.repository.ConferenceRoomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ConferenceRoomReleaseScheduler {
    private static final Logger log = LoggerFactory.getLogger(ConferenceRoomReleaseScheduler.class);
    final ConferenceRoomRepository roomRepository;
    final ConferenceRoomBookingRepository bookingRepository;


    @Scheduled(fixedRateString = "${app.scheduler.roomRelease.fixedRate}")
    @Transactional
    public void releaseExpiredBookings() {
        log.info("Starting the scheduled task to release expired bookings");
        try {
            val now = LocalDateTime.now();
            val expiredBookings = bookingRepository.findByIsRoomReleasedFalseAndEndTimeBetween(LocalDateTime.of(now.toLocalDate(), LocalTime.MIN), now);
            if (expiredBookings.isEmpty()) {
                log.warn("No booking to release");
            }
            expiredBookings.forEach(booking -> {
                log.debug("Releasing the room for booking id: {}", booking.getId());

                booking.setRoomReleased(true);
                val room = booking.getRoom();
                room.setBooked(false);

                roomRepository.save(room);
                bookingRepository.save(booking);
            });
            log.info("Finished the scheduled task to release expired bookings");
        } catch (DataAccessException dae) {
            log.error("Database error during room release: {}", dae.getMessage(), dae);
            throw new RuntimeException("Database error during room release", dae);
        } catch (Exception e) {
            log.error("Unexpected error during room release: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error during room release", e);
        }
    }
}
