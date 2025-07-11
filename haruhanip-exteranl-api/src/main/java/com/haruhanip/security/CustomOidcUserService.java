package com.haruhanip.security;

import com.haruhanip.api.user.service.OAuthService;
import com.haruhanip.api.user.service.UserService;
import com.haruhanip.domains.user.domain.OAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final UserService userService; // 우리 유저 서비스 (UserRepository 기반)
    private final OAuthService oAuthService; // OAuth 서비스 (OAuthIdentityRepository 기반)

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        OAuthProvider provider = OAuthProvider.from(userRequest.getClientRegistration().getRegistrationId());
        String sub = oidcUser.getSubject();
        String username = null;
        String profileImageUrl = null;

        // 필요 시 분기
        switch (provider) {
            case KAKAO -> {
                username = oidcUser.getNickName();
                profileImageUrl = oidcUser.getPicture() != null ? oidcUser.getPicture().toString() : null;
            }
            case NAVER -> {
                Map<String, Object> attributes = oidcUser.getAttributes();
                @SuppressWarnings("unchecked")
                Map<String, Object> response
                        = (Map<String, Object>) attributes.get("response");
                username = (String) response.get("nickname");
                profileImageUrl = (String) response.get("profile_image");
            }
            case GOOGLE -> {

            }
        }

        // 유저 가입 유무 체크
        if (oAuthService.findWithUserBySubAndProvider(sub, provider).isEmpty()){
            // 빈 사용자 생성
        	userService.createUser(sub, provider, username, profileImageUrl);
        }

        return new CustomOidcUser(oidcUser, provider);
    }
}
