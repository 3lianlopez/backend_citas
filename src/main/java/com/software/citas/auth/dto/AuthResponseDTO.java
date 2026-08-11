package com.software.citas.auth.dto;

public record AuthResponseDTO(
        String token,
        String tokenType,
        long expiresIn
)
    {
    public AuthResponseDTO(String token, long expiresIn) {
        this(token, "Bearer", expiresIn);
    }
}
