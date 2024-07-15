package com.mashreq.crb.swagger;

import com.mashreq.crb.dto.ConferenceRoomBookingRequest;
import com.mashreq.crb.dto.ConferenceRoomBookingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Operation(
        summary = "Book a Conference Room",
        description = "This operation allows a user to book a conference room by providing the necessary details in the request body. " +
                "The details include the start time, end time, and the number of attendees. The operation will return a 201 response with" +
                " the details of the booked room if the operation is successful. If the room is not available or the provided details are invalid, " +
                "it will return a 404 response. In case of any server error, it will return a 500 response."
)
@ApiResponses({
        @ApiResponse(responseCode = "201",
                content = { @Content(schema = @Schema(implementation = ConferenceRoomBookingResponse.class),
                        mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema() )}),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema() )})
})
@RequestBody(
        required = true,
        content = @Content(
                schema = @Schema(implementation = ConferenceRoomBookingRequest.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
        )
)
@Retention(RetentionPolicy.RUNTIME)
public @interface BookConferenceRoomApi {
}
