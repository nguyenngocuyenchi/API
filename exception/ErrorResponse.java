package com.example.DataSample.exception;

import lombok.*;

@AllArgsConstructor
@Data
public class ErrorResponse {
    int codeStatus;
    String message;
}
