package com.software.citas.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 64)
    private String codigoHash;

    @Column(nullable = false)
    private LocalDateTime expiraEn;

    @Builder.Default
    @Column(nullable = false)
    private Integer intentos = 0;

    @Builder.Default
    @Column(nullable = false)
    private Boolean usado = false;

    @Column(nullable = false)
    private LocalDateTime creadoEn;

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }
}