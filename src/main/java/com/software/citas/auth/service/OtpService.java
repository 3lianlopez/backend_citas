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
        Long usuarioId = usuario.getId();
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicioVentana = ahora.minusMinutes(SEND_WINDOW_MINUTES);

        long envios = otpCodeRepository
                .countByUsuarioIdAndCreadoEnAfter(usuarioId, inicioVentana);

        if (envios >= MAX_SENDS) {
            throw new OtpLimitExceededException(
                    "Has solicitado demasiados códigos. " +
                            "Inténtalo nuevamente en unos minutos."
            );
        }

        // Invalida el último código que todavía no haya sido utilizado.
        otpCodeRepository
                .findFirstByUsuarioIdAndUsadoFalseOrderByCreadoEnDesc(usuarioId)
                .ifPresent(anterior -> anterior.setUsado(true));

        String otp = String.format(
                "%06d",
                secureRandom.nextInt(1_000_000)
        );

        OtpCode nuevoOtp = OtpCode.builder()
                .usuario(usuario)
                .codigoHash(passwordEncoder.encode(otp))
                .expiraEn(ahora.plusMinutes(EXPIRATION_MINUTES))
                .intentos(0)
                .usado(false)
                .creadoEn(ahora)
                .build();

        otpCodeRepository.save(nuevoOtp);

        // Entregar solamente al componente encargado de enviar el correo/SMS.
        // Nunca registrarlo en logs ni devolverlo en la respuesta HTTP.
        return otp;
    }

    @Transactional(dontRollbackOn = InvalidOtpException.class)
    public void verifyOtp(Long usuarioId, String otp) {
        LocalDateTime ahora = LocalDateTime.now();

        OtpCode otpCode = otpCodeRepository
                .findFirstByUsuarioIdAndUsadoFalseAndExpiraEnAfterOrderByCreadoEnDesc(
                        usuarioId,
                        ahora
                )
                .orElseThrow(this::invalidOtp);

        if (otpCode.getIntentos() >= MAX_ATTEMPTS) {
            otpCode.setUsado(true);
            throw invalidOtp();
        }

        if (!passwordEncoder.matches(otp, otpCode.getCodigoHash())) {
            int intentos = otpCode.getIntentos() + 1;
            otpCode.setIntentos(intentos);

            if (intentos >= MAX_ATTEMPTS) {
                otpCode.setUsado(true);
            }

            throw invalidOtp();
        }

        otpCode.setUsado(true);
    }

    private InvalidOtpException invalidOtp() {
        return new InvalidOtpException(
                "Código OTP inválido o expirado"
        );
    }
}