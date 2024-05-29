package com.vladyslavberezovskyi.security;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.vladyslavberezovskyi.error.ForbiddenException;
import com.vladyslavberezovskyi.security.enums.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@NoArgsConstructor
public class TokenBasedAuthFilter extends OncePerRequestFilter {

    private final IgnoredPathProperties ignoredPathProperties = new IgnoredPathProperties();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        boolean ignored = ignoredPathProperties.getAntMatchers().stream().anyMatch(ignoredPath -> ignoredPath.matches(request));

        if (!ignored) {
            boolean tokenIsPresent = isNotBlank(request.getHeader("Authorization"));

            if (tokenIsPresent) {
                try {
                    establishUserContext(request);
                } catch (ParseException e) {
                    throw new ForbiddenException();
                }
            } else {
                throw new ForbiddenException();
            }
        }

        filterChain.doFilter(request, response);
    }

    private void establishUserContext(HttpServletRequest request) throws ParseException {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        JWTClaimsSet jWtClaimsSet = JWTParser.parse(token).getJWTClaimsSet();
        UserDetails userDetails = UserDetails.builder()
                .id(UUID.fromString(jWtClaimsSet.getStringClaim("id")))
                .email(jWtClaimsSet.getStringClaim("email"))
                .password(jWtClaimsSet.getStringClaim("password"))
                .role(Role.valueOf(jWtClaimsSet.getStringClaim("role")))
                .enabled(jWtClaimsSet.getBooleanClaim("enabled"))
                .build();

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails, userDetails.getAuthorities()));
    }
}
