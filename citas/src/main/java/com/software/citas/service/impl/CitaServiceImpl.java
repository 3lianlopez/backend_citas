package com.software.citas.service.impl;

import com.software.citas.dto.response.CitaResponse;
import com.software.citas.dto.request.CreateCitaRequest;
import com.software.citas.dto.request.UpdateCitaRequest;
import com.software.citas.entity.Cita;
import com.software.citas.mapper.CitaMapper;
import com.software.citas.repository.CitaRepository;
import com.software.citas.service.CitaService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final CitaMapper citaMapper;

    public  CitaServiceImpl(CitaRepository citaRepository, CitaMapper citaMapper) {
        this.citaRepository = citaRepository;
        this.citaMapper = citaMapper;
    }


    @Override
    public CitaResponse create(CreateCitaRequest request) {

        Cita cita = new Cita();

        cita.setTipoCita(request.getTipoCita());
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());

        Cita citaGuardada = citaRepository.save(cita);

        return new CitaResponse(
                citaGuardada.getId(),
                citaGuardada.getTipoCita(),
                citaGuardada.getFecha(),
                citaGuardada.getHora()
        );
    }


    @Override
    public List<CitaResponse> findAll() {

        return citaRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(citaMapper::toResponse)
                .toList();
    }

    @Override
    public CitaResponse update(Long id, UpdateCitaRequest request) {

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        cita.setTipoCita(request.getTipoCita());
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());

        Cita citaActualizada = citaRepository.save(cita);

        return new CitaResponse(
                citaActualizada.getId(),
                citaActualizada.getTipoCita(),
                citaActualizada.getFecha(),
                citaActualizada.getHora()
        );
    }

    @Override
    public void delete(Long id) {

        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("La cita no existe");
        }

        citaRepository.deleteById(id);
    }
}
