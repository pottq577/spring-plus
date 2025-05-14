package org.example.expert.domain.common.dto;

import lombok.Getter;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.global.security.UserPrincipal;

@Getter
public class AuthUser {

    private final Long id;
    private final String email;
    private final UserRole userRole;

    public AuthUser(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }

    public static AuthUser of(UserPrincipal principal){
        return new AuthUser(
            principal.getUserId(),
            principal.getEmail(),
            principal.getRole()
        );
    }
}
