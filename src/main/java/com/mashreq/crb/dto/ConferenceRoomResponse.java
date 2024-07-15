package com.mashreq.crb.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRoomResponse {
    Long id;
    String name;
    int capacity;
    List<MaintenanceWindow> maintenanceWindow;
    boolean isBooked;
}
