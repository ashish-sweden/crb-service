package com.mashreq.crb.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.mashreq.crb.dto.ConferenceRoomRequest
import com.mashreq.crb.service.ConferenceRoomService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.LocalTime
import java.time.format.DateTimeFormatter

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ConferenceRoomControllerTest extends Specification {

    private MockMvc mockMvc

    def setup() {
        ConferenceRoomService service = Mock(ConferenceRoomService)
        mockMvc = MockMvcBuilders.standaloneSetup(new ConferenceRoomController(service)).build()
    }

    def "test create"() {
        given:
        ConferenceRoomRequest request = new ConferenceRoomRequest()
        request.name = "Conference Room 1"
        request.capacity = 50
        request.maintenanceWindowIds = [1L, 2L]

        ObjectMapper objectMapper = new ObjectMapper()
        objectMapper.registerModule(new JavaTimeModule())
        String requestBody = objectMapper.writeValueAsString(request)

        when:
        def result = mockMvc.perform(post("/api/v1/conference-room/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))

        then:
        result.andExpect(status().isOk())
    }

    def "test findById"() {
        given:
        Long id = 1L

        when:
        def result = mockMvc.perform(get("/api/v1/conference-room/" + id))

        then:
        result.andExpect(status().isOk())
    }

    def "test findAllAvailable"() {
        given:
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        LocalTime startTime = LocalTime.parse("10:00:00", formatter)
        LocalTime endTime = LocalTime.parse("11:00:00", formatter)

        when:
        def result = mockMvc.perform(get("/api/v1/conference-room")
                .param("startTime", startTime.toString())
                .param("endTime", endTime.toString()))

        then:
        result.andExpect(status().isOk())
    }
}