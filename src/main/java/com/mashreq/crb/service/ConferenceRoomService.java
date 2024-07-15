package com.mashreq.crb.service;

import com.mashreq.crb.dto.ConferenceRoomRequest;
import com.mashreq.crb.dto.ConferenceRoomResponse;
import com.mashreq.crb.exception.ConferenceRoomException;
import com.mashreq.crb.mapper.ConferenceRoomMapper;
import com.mashreq.crb.repository.ConferenceRoomRepository;
import com.mashreq.crb.repository.RoomMaintenanceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ConferenceRoomService {
    private static final Logger log = LoggerFactory.getLogger(ConferenceRoomService.class);
    ConferenceRoomRepository repository;
    ConferenceRoomMapper conferenceRoomMapper;
    RoomMaintenanceRepository maintenanceRepository;

    public ConferenceRoomResponse create(ConferenceRoomRequest request) {
        try {
            log.info("Attempting to create a conference room with name: {}", request.getName());
            repository.findByName(request.getName())
                    .ifPresent(room -> {
                        throw new ConferenceRoomException("Conference Room already present with a name: " + request.getName());
                    });
            val entity = conferenceRoomMapper.mapToEntity(request);
            val maintenanceList = maintenanceRepository.findAllById(request.getMaintenanceWindowIds());
            entity.setRoomMaintenanceWindow(maintenanceList);
            return conferenceRoomMapper.mapToDto(repository.save(entity));
        } catch (Exception e) {
            log.error("Unexpected error during creation of conference room: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error during creation of conference room", e);
        }
    }
    public ConferenceRoomResponse findById(Long id) {
        try {
            log.info("Attempting to find a conference room with id: {}", id);
            return repository.findById(id)
                    .map(conferenceRoomMapper::mapToDto)
                    .orElseThrow(() -> new ConferenceRoomException("Conference room not present for Id : " + id));
        } catch (Exception e) {
            log.error("Unexpected error during retrieval of conference room: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error during retrieval of conference room", e);
        }
    }

    public List<ConferenceRoomResponse> findAvailableByTimeRange(LocalTime startTime, LocalTime endTime) {
        try {
            log.info("Attempting to find available rooms between {} and {}", startTime, endTime);
            return repository.findAvailableRooms(startTime, endTime)
                    .stream()
                    .map(conferenceRoomMapper::mapToDto)
                    .toList();
        } catch (Exception e) {
            log.error("Unexpected error during retrieval of available rooms: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error during retrieval of available rooms", e);
        }
    }
}
