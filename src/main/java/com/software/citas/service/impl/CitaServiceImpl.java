package com.software.citas.service.impl;

import com.software.citas.dto.response.CitaResponse;
import com.software.citas.dto.request.CitaDTO;
import com.software.citas.entity.Cita;
import com.software.citas.enums.EstadoCita;
import com.software.citas.exception.ResourceNotFoundException;
import com.software.citas.mapper.CitaMapper;
import com.software.citas.repository.CitaRepository;
import com.software.citas.service.CitaService;
import com.software.citas.service.state.CitaStateMachine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    private static final Logger log = LogManager.getLogger(CitaServiceImpl.class);
    private final CitaRepository citaRepository;
    private final CitaMapper citaMapper;
    private final CitaStateMachine stateMachine;

    public  CitaServiceImpl(CitaRepository citaRepository, CitaMapper citaMapper, CitaStateMachine stateMachine) {
        this.citaRepository = citaRepository;
        this.citaMapper = citaMapper;
        this.stateMachine = stateMachine;
    }


    @Override
    public CitaResponse create(CitaDTO request) {

    log.debug("REST request to save Cita : {}", request);
        Cita cita = Cita.builder()
                .documento(request.getDocumento())
                .estado(EstadoCita.PENDIENTE)
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .hora(request.getHora())
                .tipoCita(request.getTipoCita())
                .fecha(request.getFecha())
                .build();

        return guardar(cita);
    }


    @Override
    public List<CitaResponse> findAll() {

        return citaRepository.findAll(Sort.by(Sort.Direction.ASC, "fecha"))
                .stream()
                .map(citaMapper::toResponse)
                .toList();
    }

    @Override
    public CitaResponse update(Long id, CitaDTO request) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La cita con id " + id + " no existe."));
        cita.setApellidos(request.getApellidos());
        cita.setDocumento(request.getDocumento());
        cita.setNombres(request.getNombres());
        cita.setTipoCita(request.getTipoCita());
        cita.setHora(request.getHora());
        cita.setFecha(request.getFecha());


        return guardar(cita);
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

    @Override
    public CitaResponse confirmar(Long id) {

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cita no encontrada"));

        EstadoCita nuevoEstado =
                stateMachine.confirmar(cita.getEstado());

        cita.setEstado(nuevoEstado);



        return guardar(cita);
    }

    @Override
    public List<CitaResponse> findByNombre(String nombres) {
        return citaRepository.findByNombres(nombres)
                .stream()
                .map(citaMapper::toResponse)
                .toList();
    }

    @Override
    public  List<CitaResponse> findByApellido(String apellidos) {
        return citaRepository.findByApellidos(apellidos)
                .stream()
                .map(citaMapper::toResponse)
                .toList();

    }

    @Override
    public List<CitaResponse> findByDocumento(String documento) {
        return citaRepository.findByDocumento(documento)
                .stream()
                .map(citaMapper::toResponse)
                .toList();

    }

    @Override
    public List<CitaResponse> findByTipoCita(String tipoCita) {
        return citaRepository.findByTipoCita(tipoCita)
                .stream()
                .map(citaMapper::toResponse)
                .toList();

    }

    @Override
    public CitaResponse iniciar(Long id) {

        Cita cita = obtenerCita(id);

        EstadoCita nuevoEstado =
                stateMachine.iniciar(cita.getEstado());

        cita.setEstado(nuevoEstado);

        return guardar(cita);
    }

    @Override
    public CitaResponse finalizar(Long id) {

        Cita cita = obtenerCita(id);

        EstadoCita nuevoEstado =
                stateMachine.finalizar(cita.getEstado());

        cita.setEstado(nuevoEstado);

        return guardar(cita);
    }

    @Override
    public CitaResponse cancelar(Long id) {

        Cita cita = obtenerCita(id);

        EstadoCita nuevoEstado =
                stateMachine.cancelar(cita.getEstado());

        cita.setEstado(nuevoEstado);

        return guardar(cita);
    }

    private Cita obtenerCita(Long id) {

        return citaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cita no encontrada"));
    }

    private CitaResponse guardar(Cita cita) {

        Cita citaGuardada = citaRepository.save(cita);

        return citaMapper.toResponse(citaGuardada);
    }


}
