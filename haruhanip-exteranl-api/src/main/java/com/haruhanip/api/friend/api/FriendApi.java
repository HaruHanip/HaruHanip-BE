package com.haruhanip.api.friend.api;

import com.haruhanip.api.friend.dto.FriendRequestDto;
import com.haruhanip.api.friend.dto.FriendRequestResponse;
import com.haruhanip.api.friend.dto.FriendResponse;
import com.haruhanip.api.friend.dto.FriendResponseRequestDto;
import com.haruhanip.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Friend API", description = "친구 API")
public interface FriendApi {

    @Operation(summary = "친구 요청 보내기", description = "사용자가 친구 요청을 보냅니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "친구 요청 보내기 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "사용자 또는 친구를 찾을 수 없음")
    })
    public ResponseEntity<Void> sendFriendRequest(
            @RequestBody @Valid FriendRequestDto friendRequestDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );

    @Operation(summary = "친구 요청 응답", description = "사용자가 친구 요청에 응답합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "친구 요청 응답 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "친구 요청을 찾을 수 없음")
    })
    public ResponseEntity<Void> respondToFriendRequest(
            @RequestBody @Valid FriendResponseRequestDto friendResponseRequestDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );

    @Operation(summary = "친구 목록 조회", description = "사용자의 친구 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "친구 목록 조회 성공", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Friend List Example",
                                    value = """
                                            [
                                              {
                                                "userId": 1,
                                                "nickname": "friend1",
                                                "profileImage": "https://example.com/profile1.jpg"
                                                },
                                                {
                                                "userId": 2,
                                                "nickname": "friend2",
                                                "profileImage": "https://example.com/profile2.jpg"
                                                }
                                                ]
                                            """

            )})),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<List<FriendResponse>> getFriendList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );

    @Operation(summary = "친구 요청 목록 조회", description = "사용자의 친구 요청 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "친구 요청 목록 조회 성공", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Friend Request List Example",
                                    value = """
                                            [
                                              {
                                                "friendId": 1,
                                                "friendRequesterId": "1",
                                                "requesterUsername": "requester1",
                                                "friendStatus": "PENDING",
                                                },
                                                {
                                                "friendId": 2,
                                                "friendRequesterId": "2",
                                                "requesterUsername": "requester2",
                                                "friendStatus": "PENDING"
                                                }
                                                ]
                                            """
                            )
                    }
            )),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<List<FriendRequestResponse>> getFriendRequestList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );
}
