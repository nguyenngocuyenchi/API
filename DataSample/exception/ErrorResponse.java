package com.example.DataSample.exception;

public class ErrorResponse {
    private int codeStatus;
    private String message;

    public ErrorResponse(int codeStatus, String message) {
        this.codeStatus = codeStatus;
        this.message = message;
    }


}
