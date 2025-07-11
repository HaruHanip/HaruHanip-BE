package com.haruhanip.domains.friend.domain;

import com.haruhanip.domains.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "friend")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long friendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    @Builder
    public Friend(User requester, User receiver, FriendStatus status, LocalDateTime requestedAt, LocalDateTime respondedAt) {
        this.requester = requester;
        this.receiver = receiver;
        this.status = status;
        this.requestedAt = requestedAt;
        this.respondedAt = respondedAt;
    }

    public void accept() {
        this.status = FriendStatus.ACCEPTED;
        this.respondedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = FriendStatus.REJECTED;
        this.respondedAt = LocalDateTime.now();
    }

}
