package com.mashreq.crb.mapper;

import com.mashreq.crb.dto.ConferenceRoomBookingRequest;
import com.mashreq.crb.dto.ConferenceRoomBookingResponse;
import com.mashreq.crb.entity.ConferenceRoomBooking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConferenceRoomBookingMapper {

    ConferenceRoomBooking mapToEntity(ConferenceRoomBookingRequest source);

    @Mapping(target = "roomName", source = "room.name")
    ConferenceRoomBookingResponse mapToDto(ConferenceRoomBooking source);
}
