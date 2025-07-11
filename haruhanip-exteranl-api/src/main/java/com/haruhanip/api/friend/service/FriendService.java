package com.haruhanip.api.friend.service;

import com.haruhanip.api.friend.dto.*;
import com.haruhanip.domains.friend.domain.Friend;
import com.haruhanip.domains.friend.domain.FriendStatus;
import com.haruhanip.domains.friend.repository.FriendRepository;
import com.haruhanip.domains.user.domain.User;
import com.haruhanip.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public void requestFriend(Long userId, FriendRequestDto requestDto) {
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        User target = Optional.of(userRepository.findByEmail(requestDto.email()))
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + requestDto.email()));;

        if (friendRepository.existsByRequesterAndReceiver(target, requester)) {
            throw new IllegalArgumentException("Friend request already exists between these users.");
        }
        friendRepository.save(Friend.builder()
                        .requester(requester)
                        .receiver(target)
                        .status(FriendStatus.PENDING)
                        .requestedAt(LocalDateTime.now())
                .build());
    }

    @Transactional
    public void responseFriendRequest(Long userId, FriendResponseRequestDto responseDto) {
        Friend friendRequest = friendRepository.findById(responseDto.friendId())
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found with ID: " + responseDto.friendId()));
        if (!friendRequest.getReceiver().getUserId().equals(userId)) {
            throw new IllegalArgumentException("User is not the receiver of this friend request.");
        }
        switch (responseDto.friendStatus()) {
            case ACCEPTED -> {
                friendRequest.accept();
            }
            case REJECTED -> {
                friendRequest.reject();
            }
            default -> throw new IllegalArgumentException("Invalid friend status: " + responseDto.friendStatus());
        }
    }

    @Transactional
    public void removeFriend(Long userId, FriendRemoveRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        User target = userRepository.findById(request.friendId())
                .orElseThrow(() -> new IllegalArgumentException("Target user not found with ID: " + request.friendId()));
        Friend friend = friendRepository.findFriendBetween(user, target)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found with ID: " + request.friendId()));

        if (!friend.getRequester().equals(user) && !friend.getReceiver().equals(user)) {
            throw new IllegalArgumentException("User is not part of this friendship.");
        }

        friendRepository.delete(friend);
    }

    @Transactional(readOnly = true)
    public List<FriendResponse> getUserFriends(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        List<Friend> friends = friendRepository.findByRequesterOrReceiver(user, user);
        return friends.stream()
                .filter(friend -> friend.getStatus() == FriendStatus.ACCEPTED)
                .map(friend -> {
                    User friendUser = friend.getRequester().getUserId().equals(userId) ? friend.getReceiver() : friend.getRequester();
                    return FriendResponse.from(friendUser);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FriendRequestResponse> getFriendRequests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        List<Friend> requests = friendRepository.findByReceiverAndStatus(user, FriendStatus.PENDING);
        return requests.stream()
                .map(FriendRequestResponse::from)
                .toList();
    }

}
