package com.mashreq.crb.mapper;

import com.mashreq.crb.dto.ConferenceRoomRequest;
import com.mashreq.crb.dto.ConferenceRoomResponse;
import com.mashreq.crb.entity.ConferenceRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConferenceRoomMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "capacity", target = "capacity")
    ConferenceRoom mapToEntity(ConferenceRoomRequest source);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "capacity", target = "capacity")
    @Mapping(source = "roomMaintenanceWindow", target = "maintenanceWindow")
    ConferenceRoomResponse mapToDto(ConferenceRoom source);
}
