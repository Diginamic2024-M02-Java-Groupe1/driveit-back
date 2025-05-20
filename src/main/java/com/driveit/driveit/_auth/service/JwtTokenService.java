package com.driveit.driveit._auth.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;

@Service
public class JwtTokenService {
    private static final Logger logger = Logger.getLogger(JwtTokenService.class.getName());

    @Value("${jwt.secret-key}")
    private String jwtSecret;

    @Value("${jwt.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenExpiration;

    @Value("${jwt.issuer}")
    private String issuer;

    private final CollaboratorRepository collaboratorRepository;

    @Autowired
    public JwtTokenService(CollaboratorRepository collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }

    @PostConstruct
    public void validateSecrets() {
        if (jwtSecret == null || jwtSecret.equals("${JWT_SECRET}")) {
            throw new IllegalStateException("JWT_SECRET doit être défini comme variable d'environnement");
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String email, String role, int userId) {
        return generateToken(createClaims(email, role, userId), email, accessTokenExpiration);
    }

    public String generateRefreshToken(String email, String role, int userId) {
        return generateToken(createClaims(email, role, userId), email, refreshTokenExpiration);
    }

    private Map<String, Object> createClaims(String email, String role, int userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("roles",role.split(","));
        claims.put("userId", userId);
        claims.put("jti", UUID.randomUUID().toString());
        return claims;
    }

    private String generateToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getRoleFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("role", String.class));
    }

    public String getJtiFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("jti", String.class));
    }

    public Short getUserIdFromToken(String token) {
        return getClaimFromToken(token, claims -> {
            Integer userId = claims.get("userId", Integer.class);
            return userId.shortValue();
        });
    }

    public List<String> getRolesFromToken(String token) {
        try {
            String[] roles = getClaimFromToken(token, claims -> claims.get("roles", String[].class));
            return roles != null ? Arrays.asList(roles) : Collections.singletonList(getRoleFromToken(token));
        } catch (Exception e) {
            return Collections.singletonList(getRoleFromToken(token));
        }
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.warning("Signature JWT invalide");
        } catch (MalformedJwtException e) {
            logger.warning("JWT malformé");
        } catch (ExpiredJwtException e) {
            logger.warning("JWT expiré");
        } catch (UnsupportedJwtException e) {
            logger.warning("JWT non supporté");
        } catch (IllegalArgumentException e) {
            logger.warning("JWT vide");
        }
        return false;
    }

    public boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Collaborator getCollaboratorFromToken(String token) {
        String email = getEmailFromToken(token);
        return collaboratorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Collaborateur non trouvé pour l'email du token"));
    }
}
