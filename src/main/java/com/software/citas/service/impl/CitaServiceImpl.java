package com.software.citas.service.impl;

import com.software.citas.dto.response.CitaResponse;
import com.software.citas.dto.request.CreateCitaRequest;
import com.software.citas.dto.request.UpdateCitaRequest;
import com.software.citas.entity.Cita;
import com.software.citas.exception.ResourceNotFoundException;
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
        Cita cita = citaMapper.toEntity(request);
        cita = citaRepository.save(cita);
        return citaMapper.toResponse(cita);
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
//        Cita cita = citaRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("La cita con id " + id + " no existe."));
//        cita.setTipoCita(request.getTipoCita());
//        cita.setHora(request.getHora());
//        cita.setFecha(request.getFecha());
//
//        cita = citaRepository.save(cita);
//
//        return citaMapper.toResponse(cita);
        throw new RuntimeException("ENTRO AL PUT");
    }

    @Override
    public void delete(Long id) {

        Cita cita = citaRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("La cita con id " + id + " no existe."));
        citaRepository.delete(cita);
    }

    @Override
    public CitaResponse findById(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La cita con id " + id + " no existe."));
        return citaMapper.toResponse(cita);
    }
}
