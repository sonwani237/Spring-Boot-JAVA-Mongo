package com.example.demo.payload.response;

import java.util.Objects;

public class ResponseModel {
    private boolean status;
    private String message;
    private Object response;

    public ResponseModel(boolean mStatus, String mMessage, Object mResponse) {
        this.status = mStatus;
        this.message = mMessage;
        this.response = mResponse;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
