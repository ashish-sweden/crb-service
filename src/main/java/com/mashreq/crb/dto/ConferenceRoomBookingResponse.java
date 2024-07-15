package com.mashreq.crb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mashreq.crb.constants.AppConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRoomBookingResponse {

    Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.DATE_TIME_PATTERN)
    LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.DATE_TIME_PATTERN)
    LocalDateTime endTime;
    int attendees;
    String roomName;
    boolean isRemoved;
}
