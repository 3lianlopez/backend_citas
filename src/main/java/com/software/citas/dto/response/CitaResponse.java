package com.software.citas.dto.response;

import com.software.citas.enums.EstadoCita;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaResponse {

    private Long id;
    private EstadoCita estado;
    private String documento;
    private String nombres;
    private String apellidos;
    private String tipoCita;
    private LocalDate fecha;
    private LocalTime hora;

}