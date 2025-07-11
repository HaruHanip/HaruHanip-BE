package com.haruhanip.api.friend.controller;

import com.haruhanip.api.friend.api.FriendApi;
import com.haruhanip.api.friend.dto.*;
import com.haruhanip.api.friend.service.FriendService;
import com.haruhanip.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friend")
@Validated
public class FriendController implements FriendApi {

    private final FriendService friendService;

    @PostMapping("/request")
    public ResponseEntity<Void> sendFriendRequest(
            @RequestBody @Valid FriendRequestDto friendRequestDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ) {
        friendService.requestFriend(customUserDetails.getId(), friendRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/response")
    public ResponseEntity<Void> respondToFriendRequest(
            @RequestBody @Valid FriendResponseRequestDto friendResponseRequestDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ) {
        friendService.responseFriendRequest(customUserDetails.getId(), friendResponseRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFriend(
            @RequestBody @Valid FriendRemoveRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ) {
        friendService.removeFriend(customUserDetails.getId(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FriendResponse>> getFriendList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ) {
        List<FriendResponse> friends = friendService.getUserFriends(customUserDetails.getId());
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/request")
    public ResponseEntity<List<FriendRequestResponse>> getFriendRequestList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ) {
        List<FriendRequestResponse> friendRequests = friendService.getFriendRequests(customUserDetails.getId());
        return ResponseEntity.ok(friendRequests);
    }
}
