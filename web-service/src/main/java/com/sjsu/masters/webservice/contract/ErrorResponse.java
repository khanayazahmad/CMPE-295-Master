package com.sjsu.masters.webservice.contract;

import org.springframework.stereotype.Component;

@Component
public class ErrorResponse {

    private int code;
    private String status;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(int code, String status, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
