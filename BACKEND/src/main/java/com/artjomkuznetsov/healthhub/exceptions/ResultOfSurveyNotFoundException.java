package com.artjomkuznetsov.healthhub.exceptions;

public class ResultOfSurveyNotFoundException extends RuntimeException {
    public ResultOfSurveyNotFoundException(Long id, Long medCardId) {
        super("Could not find result of survey " + id + " in medcard " + medCardId);
    }
}
