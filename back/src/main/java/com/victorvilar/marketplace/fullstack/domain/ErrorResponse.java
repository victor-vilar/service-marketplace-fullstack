package com.victorvilar.marketplace.fullstack.domain;

import java.time.Instant;

public class ErrorResponse {

    private int httpStatus;
    private String message;
    private String timestamp;

    public ErrorResponse(int httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
        this.timestamp = Instant.now().toString();
    }


    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
