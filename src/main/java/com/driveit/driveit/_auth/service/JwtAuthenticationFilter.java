package com.driveit.driveit._auth.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final TokenBlacklistService tokenBlacklistService;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenService jwtTokenService,
                                   TokenBlacklistService tokenBlacklistService) {
        this.jwtTokenService = jwtTokenService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        boolean isAuthPath = path.contains("/auth/login") || path.contains("/auth/refresh");

        if (isAuthPath) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = extractJwtFromCookie(request);

            if (jwt == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT manquant");
                return;
            }

            String jti = jwtTokenService.getJtiFromToken(jwt);
            if (tokenBlacklistService.isJtiBlacklisted(jti)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token révoqué");
                return;
            }

            if (jwtTokenService.isTokenExpired(jwt)) {
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtTokenService.validateToken(jwt) && !tokenBlacklistService.isBlacklisted(jwt)) {
                String email = jwtTokenService.getEmailFromToken(jwt);
                List<String> roles = jwtTokenService.getRolesFromToken(jwt);

                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        email, null, authorities);
                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Erreur d'authentification");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
