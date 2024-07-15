package com.mashreq.crb.controller;

import com.mashreq.crb.dto.ConferenceRoomBookingRequest;
import com.mashreq.crb.dto.ConferenceRoomBookingResponse;
import com.mashreq.crb.service.ConferenceRoomBookingService;
import com.mashreq.crb.swagger.BookConferenceRoomApi;
import com.mashreq.crb.swagger.DeleteConferenceRoomBookingApi;
import com.mashreq.crb.swagger.GetAllConferenceBookingsApi;
import com.mashreq.crb.swagger.GetConferenceRoomBookingApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "ConferenceRoomBooking", description = "APIs to manage the bookings of conference rooms")
@Validated
public class ConferenceRoomBookingController {

    ConferenceRoomBookingService roomBookingService;
    @PostMapping
    @BookConferenceRoomApi
    public ResponseEntity<ConferenceRoomBookingResponse> book(@RequestBody @Valid ConferenceRoomBookingRequest request) {
        return ResponseEntity.ok(roomBookingService.book(request));
    }

    @GetMapping("/{id}")
    @GetConferenceRoomBookingApi
    public ResponseEntity<ConferenceRoomBookingResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(roomBookingService.findById(id));
    }

    @GetMapping
    @GetAllConferenceBookingsApi
    public ResponseEntity<List<ConferenceRoomBookingResponse>> findAll() {
        return ResponseEntity.ok(roomBookingService.findAll());
    }

    @DeleteMapping("/{id}")
    @DeleteConferenceRoomBookingApi
    public ResponseEntity<ConferenceRoomBookingResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(roomBookingService.delete(id));
    }
}
