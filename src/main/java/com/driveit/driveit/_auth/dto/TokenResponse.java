package com.driveit.driveit._auth.dto;

public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private String role;
    private int userId;
    private String nom;
    private String prenom;

    public TokenResponse(String accessToken, String refreshToken, String role, int userId, String nom, String prenom) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
        this.userId = userId;
        this.nom = nom;
        this.prenom = prenom;
    }

    // Getters
    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRole() {
        return role;
    }

    public int getUserId() {
        return userId;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
}
