package com.software.citas.service;

import com.software.citas.dto.response.CitaResponse;
import com.software.citas.dto.request.CreateCitaRequest;
import com.software.citas.dto.request.UpdateCitaRequest;

import java.util.List;

public interface CitaService {

    CitaResponse create(CreateCitaRequest request);

    List<CitaResponse> findAll();

    CitaResponse update(Long id, UpdateCitaRequest request);

    void delete(Long id);

    CitaResponse findById(Long id);
}
