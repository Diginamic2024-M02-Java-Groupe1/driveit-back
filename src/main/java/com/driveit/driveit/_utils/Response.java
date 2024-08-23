package com.driveit.driveit._utils;

public class Response {
    private String message;
    private String details;

    public Response() {
    }

    public Response(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
