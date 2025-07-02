package com.haruhanip.security;

import com.haruhanip.domains.user.domain.User;
import com.haruhanip.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(Long.parseLong(userId));
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId);
        }
        return new CustomUserDetails(
                user.get().getUserId(),
                user.get().getNickname(),
                "",
                user.get().getProfileImage(),
                List.of(new SimpleGrantedAuthority(user.get().getUserRole().name()))
        );
    }
}
