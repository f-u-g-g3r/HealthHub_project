package com.artjomkuznetsov.healthhub.exceptions;

import java.time.LocalDate;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(LocalDate date) {
        super(date + " is not a valid date.");
    }
}
