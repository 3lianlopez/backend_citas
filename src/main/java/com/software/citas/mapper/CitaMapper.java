package com.software.citas.mapper;

import com.software.citas.dto.request.CitaDTO;
import com.software.citas.dto.response.CitaResponse;
import com.software.citas.entity.Cita;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    public Cita toEntity(CitaDTO request) {

        return Cita.builder()
                .documento(request.getDocumento())
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .tipoCita(request.getTipoCita())
                .fecha(request.getFecha())
                .hora(request.getHora())
                .build();
    }

    public CitaResponse toResponse(Cita cita) {

        return CitaResponse.builder()
                .id(cita.getId())
                .documento(cita.getDocumento())
                .nombres(cita.getNombres())
                .apellidos(cita.getApellidos())
                .tipoCita(cita.getTipoCita())
                .fecha(cita.getFecha())
                .hora(cita.getHora())
                .estado(cita.getEstado())
                .build();
    }


}
