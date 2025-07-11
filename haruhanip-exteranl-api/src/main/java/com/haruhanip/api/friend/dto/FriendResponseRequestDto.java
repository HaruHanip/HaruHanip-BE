package com.haruhanip.api.friend.dto;

import com.haruhanip.domains.friend.domain.FriendStatus;

public record FriendResponseRequestDto(
        Long friendId,
        FriendStatus friendStatus
) {
}
