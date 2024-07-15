package com.mashreq.crb.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.mashreq.crb.dto.ConferenceRoomBookingRequest
import com.mashreq.crb.service.ConferenceRoomBookingService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ConferenceRoomBookingControllerTest extends Specification {

    private MockMvc mockMvc

    def setup() {
        ConferenceRoomBookingService service = Mock(ConferenceRoomBookingService)
        mockMvc = MockMvcBuilders.standaloneSetup(new ConferenceRoomBookingController(service)).build()
    }

    def "test book"() {
        given:
        ConferenceRoomBookingRequest request = new ConferenceRoomBookingRequest()
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        request.startTime = LocalDateTime.parse("15-07-2024 05:00", formatter)
        request.endTime = LocalDateTime.parse("15-07-2024 05:30", formatter)
        request.attendees = 2

        ObjectMapper objectMapper = new ObjectMapper()
        objectMapper.registerModule(new JavaTimeModule())
        String requestBody = objectMapper.writeValueAsString(request)

        when:
        def result = mockMvc.perform(post("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))

        then:
        result.andExpect(status().isOk())
    }

    def "test findById"() {
        given:
        Long id = 1L

        when:
        def result = mockMvc.perform(get("/api/v1/book/" + id))

        then:
        result.andExpect(status().isOk())
    }

    def "test findAll"() {
        when:
        def result = mockMvc.perform(get("/api/v1/book"))

        then:
        result.andExpect(status().isOk())
    }

    def "test delete"() {
        given:
        Long id = 1L

        when:
        def result = mockMvc.perform(delete("/api/v1/book/" + id))

        then:
        result.andExpect(status().isOk())
    }
}