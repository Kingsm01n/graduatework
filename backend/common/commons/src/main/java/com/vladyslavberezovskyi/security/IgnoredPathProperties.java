package com.vladyslavberezovskyi.security;

import lombok.Getter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class IgnoredPathProperties {

    private static final Set<String> IGNORE = new HashSet<>(List.of(new String[]{"/actuator/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/favicon.ico",
            "/api/v1/users",
            "/api/v1/users/auth",
            "/api/v1/invitations/confirmInvitation/**"}));

    private final Set<AntPathRequestMatcher> antMatchers;

    public IgnoredPathProperties() {
        this.antMatchers = IGNORE.stream().map(AntPathRequestMatcher::new).collect(Collectors.toSet());
    }

}