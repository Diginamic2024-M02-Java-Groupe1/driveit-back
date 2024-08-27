package com.driveit.driveit._utils;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Response {
    private String message;
    private int code;


    private ArrayList<String> errors;
    public Response() {
    }


    public Response(String message) {
        this.message = message;
        this.code = 200;
    }

    public Response(int code, String message, ArrayList<String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public int getCode() {
        return code;
    }
}
