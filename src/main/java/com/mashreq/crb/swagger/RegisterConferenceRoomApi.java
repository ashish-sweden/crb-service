package com.mashreq.crb.swagger;

import com.mashreq.crb.dto.ConferenceRoomRequest;
import com.mashreq.crb.dto.ConferenceRoomResponse;
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
        summary = "Register a New Conference Room",
        description = "This operation allows a user to register a new conference room by providing the necessary details in the request body. " +
                "The details include the room number, capacity, and any special features. The operation will return a 201 response with the details of the newly created room if the operation is successful." +
                " If the provided details are invalid, it will return a 400 response. In case of any server error, it will return a 500 response."
)
@ApiResponses({
        @ApiResponse(responseCode = "201",
                content = { @Content(schema = @Schema(implementation = ConferenceRoomResponse.class),
                        mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema() )}),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema() )})
})
@RequestBody(
        required = true,
        content = @Content(
                schema = @Schema(implementation = ConferenceRoomRequest.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
        )
)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterConferenceRoomApi {
}
