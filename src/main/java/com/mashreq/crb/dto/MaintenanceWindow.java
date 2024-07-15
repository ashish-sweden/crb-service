package com.mashreq.crb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mashreq.crb.constants.AppConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceWindow {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.TIME_PATTERN)
    @Schema(type = "string", pattern = AppConstants.TIME_PATTERN, example = "09:00")
    @NotNull(message = "Start time can not be null")
    LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.TIME_PATTERN)
    @Schema(type = "string", pattern = AppConstants.TIME_PATTERN, example = "09:15")
    @NotNull(message = "End time can not be null")
    LocalTime endTime;
}
