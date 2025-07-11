package com.haruhanip.api.friend.dto;

import com.haruhanip.domains.friend.domain.Friend;
import com.haruhanip.domains.friend.domain.FriendStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendRequestResponse {
    private Long friendId;
    private Long friendRequesterId;
    private String requesterProfileImageUrl;
    private String requesterUsername;
    private FriendStatus friendStatus;

    @Builder
    public FriendRequestResponse(Long friendId, Long friendRequesterId, String requesterProfileImageUrl, String requesterUsername, FriendStatus friendStatus) {
        this.friendId = friendId;
        this.friendRequesterId = friendRequesterId;
        this.requesterProfileImageUrl = requesterProfileImageUrl;
        this.requesterUsername = requesterUsername;
        this.friendStatus = friendStatus;
    }

    public static FriendRequestResponse from(Friend friend) {
        return FriendRequestResponse.builder()
                .friendId(friend.getFriendId())
                .friendRequesterId(friend.getRequester().getUserId())
                .requesterProfileImageUrl(friend.getRequester().getProfileImage())
                .requesterUsername(friend.getRequester().getUsername())
                .friendStatus(friend.getStatus())
                .build();
    }
}
