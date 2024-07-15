package com.mashreq.crb.swagger;

import com.mashreq.crb.dto.ConferenceRoomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Operation(
        summary = "Get Conference Room Details",
        description = "This operation retrieves the details of a specific conference room by its ID. " +
                "The details include the room number, capacity, and any special features. If the room with the provided ID exists, it will return a 200 response with the room details." +
                " If the room does not exist, it will return a 404 response. In case of any server error, it will return a 500 response."
)
@ApiResponses({
        @ApiResponse(responseCode = "200",
                content = { @Content(schema = @Schema(implementation = ConferenceRoomResponse.class),
                        mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema() )}),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema() )})
})
@Retention(RetentionPolicy.RUNTIME)
public @interface GetConferenceRoomApi {
}
