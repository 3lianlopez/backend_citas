package com.software.citas.mapper;

import com.software.citas.dto.response.CitaResponse;
import com.software.citas.entity.Cita;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    public CitaResponse toResponse(Cita cita) {
        return new CitaResponse(
                cita.getId(),
                cita.getTipoCita(),
                cita.getFecha(),
                cita.getHora()
        );
    }
}
