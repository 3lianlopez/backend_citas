package com.software.citas.auth.repository;

import com.software.citas.auth.entity.OtpCode;
import com.software.citas.auth.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {

    Optional<OtpCode> findTopByUsuarioAndUsadoFalseOrderByCreadoEnDesc(
            Usuario usuario
    );

    Optional<OtpCode> findTopByUsuarioOrderByCreadoEnDesc(Usuario usuario);
    long countByUsuarioAndCreadoEnAfter(
            Usuario usuario,
            LocalDateTime fecha
    );
}