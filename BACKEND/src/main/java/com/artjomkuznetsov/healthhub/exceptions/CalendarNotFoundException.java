package com.artjomkuznetsov.healthhub.exceptions;

public class CalendarNotFoundException extends RuntimeException{
    public CalendarNotFoundException(Long id) {
        super("Could not find calendar " + id);
    }
}
