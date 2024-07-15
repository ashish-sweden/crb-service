package com.mashreq.crb.controller;

import com.mashreq.crb.dto.ConferenceRoomRequest;
import com.mashreq.crb.dto.ConferenceRoomResponse;
import com.mashreq.crb.service.ConferenceRoomService;
import com.mashreq.crb.swagger.RegisterConferenceRoomApi;
import com.mashreq.crb.swagger.GetAllConferenceRoomsApi;
import com.mashreq.crb.swagger.GetConferenceRoomApi;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/conference-room")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "ConferenceRoomManagement", description = "APIs to manage the bookings and availability of conference rooms")
@Validated
public class ConferenceRoomController {

    ConferenceRoomService conferenceRoomService;

    @PostMapping("/register")
    @RegisterConferenceRoomApi
    public ResponseEntity<ConferenceRoomResponse> create(@Valid @RequestBody ConferenceRoomRequest request) {
        return ResponseEntity.ok(conferenceRoomService.create(request));
    }

    @GetMapping("/{id}")
    @GetConferenceRoomApi
    public ResponseEntity<ConferenceRoomResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(conferenceRoomService.findById(id));
    }

    @GetMapping(params = {"startTime", "endTime"})
    @GetAllConferenceRoomsApi
    public ResponseEntity<List<ConferenceRoomResponse>> findAllAvailable(
            @Parameter(description = "Start time in HH:mm:ss format", example = "10:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @Parameter(description = "End time in HH:mm:ss format", example = "10:30:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        return ResponseEntity.ok(conferenceRoomService.findAvailableByTimeRange(startTime, endTime));
    }

}
