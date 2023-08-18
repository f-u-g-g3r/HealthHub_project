package com.artjomkuznetsov.healthhub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MedCardNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(MedCardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String medCardNotFoundHandler(MedCardNotFoundException ex) {
        return ex.getMessage();
    }
}
