package com.software.citas.auth.service;


import com.software.citas.auth.dto.AuthResponseDTO;
import com.software.citas.auth.dto.LoginRequest;
import com.software.citas.auth.dto.RegisterRequest;
import com.software.citas.auth.entity.OtpCode;
import com.software.citas.auth.entity.Usuario;
import com.software.citas.auth.repository.OtpCodeRepository;
import com.software.citas.auth.repository.UsuarioRepository;
import com.software.citas.exception.InvalidOtpException;
import com.software.citas.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Locale;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final OtpCodeRepository otpCodeRepository;
    private final OtpService otpService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public void register(RegisterRequest request) {
        String email = normalizarEmail(request.getEmail());

        if (usuarioRepository.existsByEmail(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe una cuenta con ese correo"
            );
        }


        Usuario usuario = Usuario.builder()
                .nombres(request.getNombres().trim())
                .apellidos(request.getApellidos().trim())
                .email(email)
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .activo(false)
                .creadoEn(LocalDateTime.now())
                .build();

        usuarioRepository.save(usuario);

        String otp = otpService.generateOtp(usuario);
        emailService.enviarOtp(usuario.getEmail(), otp);
    }

    public void requestOtp(String email) {
        Usuario usuario = buscarUsuario(normalizarEmail(email));

        if (Boolean.TRUE.equals(usuario.getActivo())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cuenta ya está verificada"
            );
        }

        String otp = otpService.generateOtp(usuario);
        emailService.enviarOtp(usuario.getEmail(), otp);
    }

    @Transactional(dontRollbackOn = InvalidOtpException.class)
    public void verifyOtp(String email, String otp) {
        Usuario usuario = usuarioRepository
                .findByEmail(normalizarEmail(email))
                .orElseThrow(this::invalidOtp);

        if (usuario.getActivo()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cuenta ya está verificada"
            );
        }

        otpService.verifyOtp(usuario.getId(), otp);

        usuario.setActivo(true);
    }

    public AuthResponseDTO login(LoginRequest request) {
        Usuario usuario = buscarUsuario(normalizarEmail(request.getEmail()));

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Debes verificar tu correo antes de iniciar sesión"
            );
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw credencialesInvalidas();
        }

        String token = jwtService.generarToken(usuario);
        return new AuthResponseDTO(token, jwtService.getExpiracionMs() / 1000);
    }

    private Usuario buscarUsuario(String email) {
        return usuarioRepository.findByEmail(normalizarEmail(email))
                .orElseThrow(this::credencialesInvalidas);
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase();
    }

    private ResponseStatusException credencialesInvalidas() {
        return new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Correo o contraseña incorrectos"
        );
    }

    private InvalidOtpException invalidOtp() {
        return new InvalidOtpException("Código OTP inválido o expirado");
    }
}