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
        summary = "Get Available Conference Rooms",
        description = "This operation retrieves the list of conference rooms that are available within a specified range of time." +
                " The user needs to provide the start time and end time for the range. Each room detail includes the room number, capacity, and any special features." +
                " If there are any rooms available within the given time range, it will return a 200 response with the list of room details. " +
                "If there are no rooms available, it will return a 404 response. In case of any server error, it will return a 500 response."
)
@ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {@Content(schema = @Schema(implementation = ConferenceRoomResponse.class),
                        mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
})
@Retention(RetentionPolicy.RUNTIME)
public @interface GetAllConferenceRoomsApi {
}
