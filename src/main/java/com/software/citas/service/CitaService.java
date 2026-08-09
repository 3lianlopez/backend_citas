package com.software.citas.service;

import com.software.citas.dto.response.CitaResponse;
import com.software.citas.dto.request.CitaDTO;

import java.time.LocalDate;
import java.util.List;

public interface CitaService {

    CitaResponse create(CitaDTO request);

    List<CitaResponse> findAll();

    CitaResponse update(Long id, CitaDTO request);

    void delete(Long id);

    CitaResponse findById(Long id);

    CitaResponse confirmar(Long id);

    List<CitaResponse> findByNombre(String nombre);
    List<CitaResponse> findByApellido(String apellidos);
    List<CitaResponse> findByDocumento(String documento);
    List<CitaResponse> findByTipoCita(String tipoCita);

    CitaResponse iniciar(Long id);

    CitaResponse finalizar(Long id);

    CitaResponse cancelar(Long id);

}
