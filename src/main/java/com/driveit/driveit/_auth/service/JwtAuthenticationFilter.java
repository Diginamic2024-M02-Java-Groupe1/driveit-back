package com.driveit.driveit._auth.service;

import com.driveit.driveit._utils.ErrorResponseUtil;
import com.driveit.driveit.collaborator.Collaborator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${springdoc.api-docs.path}")
    private String swaggerPath;

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
        boolean isAuthPath = path.startsWith("/auth/");
        boolean isSwaggerPath = path.equals(swaggerPath)
                || path.startsWith(swaggerPath + "/v3/api-docs")
                || path.startsWith("/v3/api-docs")
                || path.equals("/v3/api-docs/swagger-config")
                || path.startsWith("/api/configuration/ui/")
                || path.startsWith("/swagger-resources/")
                || path.startsWith("/configuration/security")
                || path.equals("/swagger-ui.html")
                || path.startsWith("/swagger-ui/")
                || path.startsWith("/webjars/");

        if (isAuthPath || isSwaggerPath) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = extractJwtFromCookie(request);

            if (jwt == null) {
                ErrorResponseUtil.sendErrorResponse(
                        response,
                        HttpStatus.UNAUTHORIZED,
                        "Token JWT manquant",
                        "Authentification échouée"
                );
                return;
            }

            String jti = jwtTokenService.getJtiFromToken(jwt);
            if (tokenBlacklistService.isJtiBlacklisted(jti)) {
                ErrorResponseUtil.sendErrorResponse(
                        response,
                        HttpStatus.UNAUTHORIZED,
                        "Token révoqué",
                        "Token non valide"
                );
                return;
            }

            if (jwtTokenService.isTokenExpired(jwt)) {
                ErrorResponseUtil.sendErrorResponse(
                        response,
                        HttpStatus.UNAUTHORIZED,
                        "Token expiré",
                        "Authentification échouée"
                );
                return;
            }

            if (jwtTokenService.validateToken(jwt) && !tokenBlacklistService.isBlacklisted(jwt)) {
                String email = jwtTokenService.getEmailFromToken(jwt);
                List<String> roles = jwtTokenService.getRolesFromToken(jwt);

                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                Collaborator collaborator = jwtTokenService.getCollaboratorFromToken(jwt);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        collaborator, null, authorities);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Continuez seulement si l'authentification réussit
                filterChain.doFilter(request, response);
            } else {
                ErrorResponseUtil.sendErrorResponse(
                        response,
                        HttpStatus.UNAUTHORIZED,
                        "Token invalide",
                        "Authentification échouée"
                );
            }
        } catch (Exception e) {
            System.out.println("Erreur d'authentification: " + e.getMessage());
            ErrorResponseUtil.sendErrorResponse(
                    response,
                    HttpStatus.UNAUTHORIZED,
                    "Erreur d'authentification: " + e.getMessage(),
                    "Échec de traitement du token"
            );
        }
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
