package com.software.citas.entity;

import com.software.citas.enums.EstadoCita;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "citas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCita estado;

    @Column(name = "documento", nullable = false)
    private String documento;

    @Column(name = "nombres", nullable = false)
    private String nombres;

    @Column(name = "apellidos" , nullable = false)
    private String apellidos;

    @Column(name = "tipo_cita", nullable = false)
    private String tipoCita;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

}