package com.software.citas.controller;

import com.software.citas.dto.response.CitaResponse;
import com.software.citas.dto.request.CreateCitaRequest;
import com.software.citas.dto.request.UpdateCitaRequest;
import com.software.citas.service.CitaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public List<CitaResponse> findAll(){
        return citaService.findAll();
    }

    @PostMapping
    public CitaResponse save(@RequestBody CreateCitaRequest request) {
        return citaService.create(request);
    }

    @PutMapping("/{id}")
    public CitaResponse update(
            @PathVariable Long id,
            @RequestBody UpdateCitaRequest request) {

        return citaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        citaService.delete(id);
    }


}
