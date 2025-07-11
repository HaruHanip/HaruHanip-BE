package com.haruhanip.security;

import com.haruhanip.api.user.service.OAuthService;
import com.haruhanip.api.user.service.UserService;
import com.haruhanip.common.jwt.JwtProvider;
import com.haruhanip.common.jwt.JwtToken;
import com.haruhanip.domains.token.repository.RefreshTokenRepository;
import com.haruhanip.domains.user.domain.OAuthIdentity;
import com.haruhanip.domains.user.domain.OAuthProvider;
import com.haruhanip.domains.user.domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final OAuthService oAuthService;
    private final UserService userService;
    @Value("${url.redirect.base}")
    private String REDIRECT_URL;
    @Value("${jwt.refresh.time}")
    private long REFRESH_TIME;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        String sub = customUser.getSubject();
        OAuthProvider provider = customUser.getProvider();

        Optional<OAuthIdentity> oAuthIdentity = oAuthService.findWithUserBySubAndProvider(sub,provider);
        Optional<User> user;

        if(oAuthIdentity.isPresent()){
            user = Optional.ofNullable(oAuthIdentity.get().getUser());
            if(user.isEmpty()) {
                throw new NoSuchElementException("사용자를 찾을 수 없음");
            }
        } else {
            throw new NoSuchElementException("사용자를 찾을 수 없음");
        }

        JwtToken token = jwtProvider.provideTokens(
                user.get().getUserId(),
                user.get().getUsername(),
                user.get().getUserRole(),
                user.get().getRegistStatus()
        );

        refreshTokenRepository.save(
                Long.toString(user.get().getUserId()),
                token.getRefreshToken(),
                REFRESH_TIME
        );

        response.addCookie(createAccessTokenCookie(token.getAccessToken()));
        response.addCookie(createRefreshTokenCookie(token.getRefreshToken()));

        response.sendRedirect(REDIRECT_URL);
    }

    private Cookie createAccessTokenCookie(String accessToken) {
        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
//        cookie.setSecure(true);
        cookie.setMaxAge(60 * 15); // 15분
        return cookie;
    }

    private Cookie createRefreshTokenCookie(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24); // 24 hours
        return cookie;
    }
}
