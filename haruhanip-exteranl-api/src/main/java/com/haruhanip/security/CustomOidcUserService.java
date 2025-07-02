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

        // 필요 시 분기
        switch (provider) {
            case KAKAO -> {

            }
            case NAVER -> {

            }
            case GOOGLE -> {

            }
        }

        // 유저 가입 유무 체크
        if (oAuthService.findWithUserBySubAndProvider(sub, provider).isEmpty()){
            // 빈 사용자 생성
        	userService.createUser(sub, provider);
        }

        return new CustomOidcUser(oidcUser, provider);
    }
}
