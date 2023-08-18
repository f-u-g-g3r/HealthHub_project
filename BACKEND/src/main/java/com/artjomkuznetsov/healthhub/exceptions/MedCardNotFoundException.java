package com.artjomkuznetsov.healthhub.exceptions;

public class MedCardNotFoundException extends RuntimeException{

    public MedCardNotFoundException(Long id) {
        super("Could not find medical card " + id);
    }
}
