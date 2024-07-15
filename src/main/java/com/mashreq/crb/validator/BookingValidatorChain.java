package com.mashreq.crb.validator;

import com.mashreq.crb.dto.ConferenceRoomBookingRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookingValidatorChain {
    List<RoomBookingValidator> validators;

    public void validate(ConferenceRoomBookingRequest bookingRequest) {
        validators.forEach(validator -> validator.validate(bookingRequest));
    }
}
