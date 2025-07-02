package com.haruhanip.common.jwt;

import com.haruhanip.domains.user.domain.RegistStatus;
import com.haruhanip.domains.user.domain.UserRole;
import com.haruhanip.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.access.time}")
    private long ACCESS_TIME;

    @Value("${jwt.refresh.time}")
    private long REFRESH_TIME;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private final @Lazy UserDetailsService userDetailsService;

    public JwtToken provideTokens(Long sub, String username, UserRole role, RegistStatus registStatus) {
        Claims claims = makeAccessClaims(sub, username, role, registStatus);
        Claims refresh_claims = makeRefreshClaims(sub);
        return JwtToken.builder()
                .accessToken(generateToken(claims, ACCESS_TIME))
                .refreshToken(generateToken(refresh_claims, REFRESH_TIME))
                .build();
    }

    public String generateToken(Claims claims, long time) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    // 이 메서드가 ttl도 검증하나?
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        String email = getClaims(token).getSubject();
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String resolveToken(String header) {
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private Claims makeAccessClaims(Long sub, String username, UserRole role, RegistStatus registStatus) {
        Claims claims = Jwts.claims();
        claims.setSubject(Long.toString(sub));
        claims.put("username", username);
        claims.put("role", role.name());
        claims.put("registStatus", registStatus.name());
        return claims;
    }

    private Claims makeRefreshClaims(Long sub) {
        Claims claims = Jwts.claims();
        claims.setSubject(Long.toString(sub));
        return claims;
    }

    public UserDetails getUserDetails(String token) {
        String userId = getClaims(token).getSubject();
        return userDetailsService.loadUserByUsername(userId);
    }

    public String extractUserId(String token) {
        return getClaims(token).getSubject();
    }

}