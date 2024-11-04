package com.example.springsecurity.security;

import com.example.springsecurity.service.BlacklistTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final BlacklistTokenService blacklistTokenService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Lấy JWT từ request.
        String token = jwtTokenProvider.getJwtFromRequest(request);

        // Nếu JWT không null và hợp lệ
        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
            // Kiem tra token nay da dang xuat va nam trong entity blacklist chưa
            if (blacklistTokenService.isTokenBlacklisted(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has been blacklisted");
                return;
            }
                Authentication auth = jwtTokenProvider.createAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
