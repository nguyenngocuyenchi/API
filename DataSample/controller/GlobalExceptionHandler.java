package com.example.DataSample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.DataSample.exception.NotFoundException;
import com.example.DataSample.exception.AlreadyExistsException;
import com.example.DataSample.exception.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleNotFoundException(NotFoundException notfound) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), notfound.getMessage());
    }
    
    @ExceptionHandler(value = AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public @ResponseBody ErrorResponse handleAlreadyExistsException(AlreadyExistsException exists) {
        return new ErrorResponse(HttpStatus.ALREADY_REPORTED.value(), exists.getMessage());
    }


}
