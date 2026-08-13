package com.software.citas.auth.controller;


import com.software.citas.auth.dto.*;
import com.software.citas.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "success", true,
                "message", "Registro creado. Revisa tu correo para obtener el OTP"
        ));
    }

//    @PostMapping("/request-otp")
//    public ResponseEntity<?> requestOtp(@Valid @RequestBody RequestOtpDTO request) {
//        authService.requestOtp(request.getEmail());
//        return ResponseEntity.ok(Map.of(
//                "success", true,
//                "message", "Si la cuenta está pendiente, se envió un nuevo OTP"
//        ));
//    }

//    @PostMapping("/verify-otp")
//    public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyOtpDTO request) {
//        authService.verifyOtp(request.getEmail(), request.getOtp());
//        return ResponseEntity.ok(Map.of(
//                "success", true,
//                "message", "Correo verificado correctamente"
//        ));
//    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}