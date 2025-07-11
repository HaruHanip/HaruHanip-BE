package com.haruhanip.domains.friend.domain;

public enum FriendStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    BLOCKED;

    public boolean isPending() {
        return this == PENDING;
    }

    public boolean isAccepted() {
        return this == ACCEPTED;
    }

    public boolean isRejected() {
        return this == REJECTED;
    }

    public boolean isBlocked() {
        return this == BLOCKED;
    }
}
