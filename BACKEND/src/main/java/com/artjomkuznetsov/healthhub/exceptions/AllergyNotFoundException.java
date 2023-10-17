package com.artjomkuznetsov.healthhub.exceptions;

public class AllergyNotFoundException extends RuntimeException {
    public AllergyNotFoundException(Long id, Long medCardId) {
        super("Could not find allergy " + id + " in medcard " + medCardId);
    }
}
