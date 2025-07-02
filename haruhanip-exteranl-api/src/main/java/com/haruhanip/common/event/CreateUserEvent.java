package com.haruhanip.common.event;

import com.haruhanip.domains.user.domain.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserEvent {
    private Long userId;
    private String sub;
    private OAuthProvider provider;
}
