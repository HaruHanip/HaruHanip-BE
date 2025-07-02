package com.haruhanip.api.user.service;

import com.haruhanip.common.event.CreateUserEvent;
import com.haruhanip.domains.user.domain.OAuthIdentity;
import com.haruhanip.domains.user.domain.OAuthProvider;
import com.haruhanip.domains.user.domain.User;
import com.haruhanip.domains.user.repository.OAuthIdentityRepository;
import com.haruhanip.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OAuthService {
    private final OAuthIdentityRepository oauthRepository;
    private final UserRepository userRepository;

    @EventListener
    public void createOAuthIdentity(CreateUserEvent event) {
        User user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없음"));
        oauthRepository.save(
                OAuthIdentity.builder()
                        .sub(event.getSub())
                        .provider(event.getProvider())
                        .user(user)
                        .build()
        );
    }

    public Optional<OAuthIdentity> findWithUserBySubAndProvider(String sub, OAuthProvider provider) {
        return oauthRepository.findBySubAndProvider(sub, provider);
    }
}
