package com.mashreq.crb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mashreq.crb.constants.AppConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConferenceRoomBookingRequest {

    @NotNull(message = "The start time of the booking must be provided.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.DATE_TIME_PATTERN)
    @Schema(type = "string", pattern = AppConstants.DATE_TIME_PATTERN, example = "10-10-2023 09:00")
    LocalDateTime startTime;

    @NotNull(message = "The end time of the booking must be provided.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.DATE_TIME_PATTERN)
    @Schema(type = "string", pattern = AppConstants.DATE_TIME_PATTERN, example = "10-10-2023 09:15")
    LocalDateTime endTime;

    @Min(value = 1, message = "The number of attendees must be at least 1.")
    int attendees;

}
