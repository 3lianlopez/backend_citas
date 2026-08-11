package com.software.citas.auth.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_usuario_email", columnNames = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nombres;

    @Column(length = 100)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 190)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    // false mientras el correo no haya sido verificado mediante OTP.
    @Builder.Default
    @Column(nullable = false)
    private Boolean activo = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creadoEn;
}
