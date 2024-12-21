package com.farevalo.candidates_api_seek.domain.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// Clase de respuesta API
@Getter
@Setter
public class CustomApiResponse {
    private String message;
    private LocalDateTime timestamp;

    public CustomApiResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters y setters
}
