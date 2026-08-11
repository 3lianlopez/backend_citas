package com.software.citas.auth.service;



import com.software.citas.auth.entity.OtpCode;
import com.software.citas.auth.entity.Usuario;
import com.software.citas.auth.repository.OtpCodeRepository;
import com.software.citas.exception.InvalidOtpException;
import com.software.citas.exception.OtpLimitExceededException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpService {

    private static final int EXPIRATION_MINUTES = 5;
    private static final int MAX_ATTEMPTS = 5;
    private static final int MAX_SENDS = 5;
    private static final int SEND_WINDOW_MINUTES = 15;

    private final OtpCodeRepository otpCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public String generateOtp(Usuario usuario) {
        LocalDateTime windowStart = LocalDateTime.now().minusMinutes(SEND_WINDOW_MINUTES);

        long envios = otpCodeRepository.countByUsuarioAndCreadoEnAfter(usuario, windowStart);
        if (envios >= MAX_SENDS) {
            throw new OtpLimitExceededException(
                    "Has solicitado demasiados códigos. Inténtalo nuevamente en unos minutos."
            );
        }

        otpCodeRepository.findTopByUsuarioAndUsadoFalseOrderByCreadoEnDesc(usuario)
                .ifPresent(anterior -> {
                    anterior.setUsado(true);
                    otpCodeRepository.save(anterior);
                });

        String otp = String.valueOf(secureRandom.nextInt(900000) + 100000);
        LocalDateTime ahora = LocalDateTime.now();

        otpCodeRepository.save(OtpCode.builder()
                .usuario(usuario)
                .codigoHash(passwordEncoder.encode(otp))
                .expiraEn(ahora.plusMinutes(EXPIRATION_MINUTES))
                .intentos(0)
                .usado(false)
                .creadoEn(ahora)
                .build());

        return otp;
    }

    @Transactional
    public void verifyOtp(OtpCode otpCode, String otp) {
        if (Boolean.TRUE.equals(otpCode.isUsado())
                || otpCode.getExpiraEn().isBefore(LocalDateTime.now())
                || otpCode.getIntentos() >= MAX_ATTEMPTS) {
            otpCode.setUsado(true);
            otpCodeRepository.save(otpCode);
            throw invalidOtp();
        }

        if (!passwordEncoder.matches(otp, otpCode.getCodigoHash())) {
            int intentos = otpCode.getIntentos() + 1;
            otpCode.setIntentos(intentos);
            if (intentos >= MAX_ATTEMPTS) {
                otpCode.setUsado(true);
            }
            otpCodeRepository.save(otpCode);
            throw invalidOtp();
        }

        otpCode.setUsado(true);
        otpCodeRepository.save(otpCode);
    }

    private InvalidOtpException invalidOtp() {
        return new InvalidOtpException("Código OTP inválido o expirado");
    }
}
