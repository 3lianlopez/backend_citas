package com.software.citas.mapper;

import com.software.citas.dto.request.CreateCitaRequest;
import com.software.citas.dto.response.CitaResponse;
import com.software.citas.entity.Cita;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    public Cita toEntity(CreateCitaRequest request) {

        return Cita.builder()
                .tipoCita(request.getTipoCita())
                .fecha(request.getFecha())
                .hora(request.getHora())
                .build();
    }

    public CitaResponse toResponse(Cita cita) {

        return CitaResponse.builder()
                .id(cita.getId())
                .tipoCita(cita.getTipoCita())
                .fecha(cita.getFecha())
                .hora(cita.getHora())
                .build();
    }


}
