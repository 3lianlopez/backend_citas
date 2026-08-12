package com.software.citas.auth.repository;


import com.software.citas.auth.entity.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {

    Optional<OtpCode>
    findFirstByUsuarioIdAndUsadoFalseOrderByCreadoEnDesc(Long usuarioId);

    Optional<OtpCode>
    findFirstByUsuarioIdAndUsadoFalseAndExpiraEnAfterOrderByCreadoEnDesc(
            Long usuarioId,
            LocalDateTime fechaActual
    );

    long countByUsuarioIdAndCreadoEnAfter(
            Long usuarioId,
            LocalDateTime fecha
    );
}