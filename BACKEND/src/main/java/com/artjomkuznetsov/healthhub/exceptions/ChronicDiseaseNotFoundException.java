package com.artjomkuznetsov.healthhub.exceptions;

public class ChronicDiseaseNotFoundException extends RuntimeException{
    public ChronicDiseaseNotFoundException(Long id, Long medCardId) {
        super("Could not find chronic disease " + id + " in medcard " + medCardId);
    }
}
