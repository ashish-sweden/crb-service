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
        summary = "Delete a Conference Room Booking",
        description = "This operation allows a user to delete a conference room booking by providing the booking ID. " +
                "The operation performs a soft delete, meaning the booking record is not permanently removed from the database but is marked as deleted. " +
                "If the operation is successful, it will return a 200 response with the details of the deleted booking. If the booking with the provided ID does not exist, " +
                "it will return a 404 response. In case of any server error, it will return a 500 response."
)
@ApiResponses({
        @ApiResponse(responseCode = "200",
                content = { @Content(schema = @Schema(implementation = ConferenceRoomBookingResponse.class),
                        mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema() )}),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema() )})
})
@Retention(RetentionPolicy.RUNTIME)
public @interface DeleteConferenceRoomBookingApi {
}
