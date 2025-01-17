package com.mashreq.crb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConferenceRoomRequest {

    @NotBlank(message = "Name can't be empty")
    String name;

    @Min(value = 1, message = "Capacity has to be greater than 1")
    int capacity;

    @NotEmpty
    List<Long> maintenanceWindowIds;
}
