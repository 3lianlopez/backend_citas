package com.software.citas.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String remitente;

    public void enviarOtp(String destinatario, String otp) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(remitente);
        mensaje.setTo(destinatario);
        mensaje.setSubject("Código de verificación");
        mensaje.setText(
                "Tu código de verificación es: " + otp + "\n\n" +
                        "El código caduca en 5 minutos. Si no lo solicitaste, ignora este mensaje."
        );

        mailSender.send(mensaje);
    }
}
