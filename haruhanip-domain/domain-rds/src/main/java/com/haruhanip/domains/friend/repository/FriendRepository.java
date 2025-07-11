package com.haruhanip.domains.friend.repository;

import com.haruhanip.domains.friend.domain.Friend;
import com.haruhanip.domains.friend.domain.FriendStatus;
import com.haruhanip.domains.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsByRequesterAndReceiver(User requester, User receiver);

    List<Friend> findByRequesterOrReceiver(User user, User user1);

    List<Friend> findByReceiverAndStatus(User user, FriendStatus friendStatus);

    @Query("""
        SELECT f
          FROM Friend f
         WHERE (f.requester = :user1 AND f.receiver = :user2)
            OR (f.requester = :user2 AND f.receiver = :user1)
    """)
    Optional<Friend> findFriendBetween(
            @Param("user1") User user1,
            @Param("user2") User user2
    );
}
