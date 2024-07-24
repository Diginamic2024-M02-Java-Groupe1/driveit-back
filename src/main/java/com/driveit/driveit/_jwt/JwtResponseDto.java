package com.driveit.driveit._jwt;

public class JwtResponseDto {
    private String accessToken;

    public String getToken() {
        return accessToken;
    }

    public void setToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
