package com.driveit.driveit._jwt;

public class JwtResponseDto {

    public JwtResponseDto(String token, Long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    private String token;

    private Long expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
