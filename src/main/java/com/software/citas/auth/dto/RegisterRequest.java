package com.software.citas.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    @Size(max = 100)
    private String nombres;

    @NotBlank
    @Size(max = 100)
    private String apellidos;

    @NotBlank
    @Email
    @Size(max = 190)
    private String email;

    @NotBlank
    @Size(min = 8, max = 72)
    private String password;
}