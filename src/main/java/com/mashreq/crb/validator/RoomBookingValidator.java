package com.mashreq.crb.validator;

import com.mashreq.crb.dto.ConferenceRoomBookingRequest;
import jakarta.validation.ValidationException;

public interface RoomBookingValidator {
    void validate(ConferenceRoomBookingRequest bookingRequest) throws ValidationException;
}
