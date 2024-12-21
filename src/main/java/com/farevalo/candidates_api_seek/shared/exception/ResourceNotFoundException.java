package com.farevalo.candidates_api_seek.shared.exception;

// Excepciones personalizadas
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
