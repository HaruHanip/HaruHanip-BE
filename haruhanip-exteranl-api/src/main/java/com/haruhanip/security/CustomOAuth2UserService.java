package com.haruhanip.security;

import com.haruhanip.api.user.service.OAuthService;
import com.haruhanip.api.user.service.UserService;
import com.haruhanip.domains.user.domain.OAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;
    private final OAuthService oAuthService;
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oauth2User.getAttributes();
        OAuthProvider provider = OAuthProvider.from(registrationId);

        String sub;
        String nickname;
        String profileImageUrl;

        switch (provider) {
            case NAVER -> {
                @SuppressWarnings("unchecked")
                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                if (response == null) {
                    throw new OAuth2AuthenticationException(
                            new OAuth2Error("invalid_user_info"),
                            "Naver user-info 응답이 없습니다."
                    );
                }
                sub = (String) response.get("id");
                nickname = (String) response.get("name");
                profileImageUrl = (String) response.get("profile_image");
            }
            default -> {
                sub = oauth2User.getName();
                nickname = oauth2User.getAttribute("name");
                profileImageUrl = oauth2User.getAttribute("picture");
            }
        }

        if (oAuthService.findWithUserBySubAndProvider(sub, provider).isEmpty()) {
            userService.createUser(sub, provider, nickname, profileImageUrl);
        }

        Map<String, Object> mappedAttributes = new HashMap<>();
        mappedAttributes.put("providerId", sub);
        mappedAttributes.put("nickname", nickname);
        mappedAttributes.put("picture", profileImageUrl);

        return new CustomOAuth2User(
                oauth2User.getAuthorities(),
                mappedAttributes,
                "providerId",
                provider
        );
    }
}
