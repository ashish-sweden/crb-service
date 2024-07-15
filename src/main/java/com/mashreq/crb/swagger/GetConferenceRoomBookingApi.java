package com.mashreq.crb.swagger;

import com.mashreq.crb.dto.ConferenceRoomBookingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Operation(
        summary = "Get Conference Room Booking Details",
        description = "This operation retrieves the details of a specific conference room booking by its ID. " +
                "The details include the start time, end time, room number, and the number of attendees." +
                " If the booking with the provided ID exists, it will return a 200 response with the booking details." +
                " If the booking does not exist, it will return a 404 response. In case of any server error, it will return a 500 response."
)
@ApiResponses({
        @ApiResponse(responseCode = "200",
                content = { @Content(schema = @Schema(implementation = ConferenceRoomBookingResponse.class),
                        mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema() )}),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema() )})
})
@Retention(RetentionPolicy.RUNTIME)
public @interface GetConferenceRoomBookingApi {
}
