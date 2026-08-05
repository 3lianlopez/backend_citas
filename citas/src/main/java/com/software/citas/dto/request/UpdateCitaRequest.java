package com.software.citas.dto.request;



import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCitaRequest {

    private String tipoCita;
    private LocalDate fecha;
    private LocalTime hora;

}