package com.driveit.driveit._auth.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenBlacklistService {
    private Set<String> blacklistedTokens = new HashSet<>();
    private Map<String, Date> expirationMap = new HashMap<>();
    private Set<String> blacklistedJtis = new HashSet<>();

    public void blacklistToken(String token, Date expiration) {
        blacklistedTokens.add(token);
        expirationMap.put(token, expiration);
    }

    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    public void blacklistJti(String jti) {
        blacklistedJtis.add(jti);
    }

    public boolean isJtiBlacklisted(String jti) {
        return blacklistedJtis.contains(jti);
    }

    @Scheduled(fixedRate = 3600000)
    public void purgeExpiredTokens() {
        Date now = new Date();
        blacklistedTokens.removeIf(token -> expirationMap.get(token).before(now));
    }
}
