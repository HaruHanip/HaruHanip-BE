package com.haruhanip.api.user.service;

import com.haruhanip.api.user.dto.RegistUserRequest;
import com.haruhanip.api.user.dto.UserProfileEditRequest;
import com.haruhanip.api.user.dto.UserProfileResponse;
import com.haruhanip.common.event.CreateUserEvent;
import com.haruhanip.common.jwt.JwtResolver;
import com.haruhanip.domains.token.repository.BlackListService;
import com.haruhanip.domains.token.repository.RefreshTokenRepository;
import com.haruhanip.domains.user.domain.OAuthProvider;
import com.haruhanip.domains.user.domain.User;
import com.haruhanip.domains.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final OAuthService oAuthService;
    private final ApplicationEventPublisher publisher;
    private final BlackListService blackListService;
    private final JwtResolver jwtResolver;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(readOnly = true)
    public List<UserProfileResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> UserProfileResponse.builder()
                        .userId(u.getUserId())
                        .nickname(u.getUsername())
                        .email(u.getEmail())
                        .profileImage(u.getProfileImage())
                        .registStatus(u.getRegistStatus())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없음"));

        return user;
    }

    @Transactional
    public void deleteUser(Long userId) {
        User existing = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + userId));

        userRepository.delete(existing);
    }

    @Transactional
    public void createUser(String sub, OAuthProvider provider, String username, String profileImageUrl) {
        // sub와 provider를 이용하여 유저가 존재하는지 확인
        if (oAuthService.findWithUserBySubAndProvider(sub, provider).isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        // 유저 정보 생성
        User user = User.builder()
                .username(username)
                .profileImage(profileImageUrl)
                .build();
        user.makeUser();
        userRepository.save(user);
        Long targetId = user.getUserId();
        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없음"));

        publisher.publishEvent(CreateUserEvent.builder()
                .userId(target.getUserId())
                .sub(sub)
                .provider(provider)
                .build());
    }

    @Transactional
    public void registUser(Long userId, RegistUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없음"));

        user.registUser(
                request.username(),
                request.email(),
                request.profileImageUrl(),
                request.birthDate()
        );
    }

    @Transactional
    public void editUserProfile(Long userId, UserProfileEditRequest userProfileEditRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없음"));

        user.updateUser(
                userProfileEditRequest.nickname(),
                userProfileEditRequest.email(),
                userProfileEditRequest.profileImageUrl(),
                userProfileEditRequest.birthday()
        );
    }

    public void logoutUser(String accessToken, String refreshToken) {
        Claims claims = jwtResolver.getClaims(jwtResolver.resolveToken(accessToken));
        Date expiration = claims.getExpiration();

        // 2) 지금과 만료 시각 간의 차이를 Duration으로 계산
        Instant now = Instant.now();
        Instant expInstant = expiration.toInstant();
        Duration secondsUntilExpiry = Duration.between(now, expInstant);
        if (secondsUntilExpiry.getSeconds() > 0) {
            blackListService.addToBlackList(accessToken, secondsUntilExpiry);
        }
        if(!refreshToken.isEmpty()) {
            refreshTokenRepository.deleteByRefreshToken(refreshToken);
        }
    }

    public UserProfileResponse getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없음"));

        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getUsername())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .role(user.getUserRole())
                .registStatus(user.getRegistStatus())
                .birthday(user.getBirthday())
                .createdAt(user.getCreatedAt())
                .build();
    }
}

