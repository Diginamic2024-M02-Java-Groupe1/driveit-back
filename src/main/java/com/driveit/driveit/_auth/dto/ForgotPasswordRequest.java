package com.driveit.driveit._auth.dto;

public class ForgotPasswordRequest {
    private String email;
    private String nom;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}