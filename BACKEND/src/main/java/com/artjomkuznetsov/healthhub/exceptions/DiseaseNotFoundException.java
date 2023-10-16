package com.artjomkuznetsov.healthhub.exceptions;

public class DiseaseNotFoundException extends RuntimeException{
    public DiseaseNotFoundException(Long id, Long medCardId) {
        super("Could not find disease " + id + " in medcard " + medCardId);
    }
}
