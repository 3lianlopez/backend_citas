package com.software.citas.controller;

import com.software.citas.dto.response.ApiResponse;
import com.software.citas.dto.response.CitaResponse;
import com.software.citas.dto.request.CreateCitaRequest;
import com.software.citas.dto.request.UpdateCitaRequest;
import com.software.citas.service.CitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<ApiResponse<List<CitaResponse>>> findAll(){
        return ApiResponse.ApiResponses.ok("Se han encontrado citas correctamente.", citaService.findAll());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CitaResponse>> create(@Validated @RequestBody CreateCitaRequest request) {
        return ApiResponse.ApiResponses.created(
                "Cita creada correctamente.",
                citaService.create(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaResponse>> findById(@PathVariable Long id){

        return ApiResponse.ApiResponses.created(
                "Cita encontrada correctamente.",
                citaService.findById(id)
        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaResponse>> update(
            @PathVariable Long id,
            @RequestBody UpdateCitaRequest request){

       return ApiResponse.ApiResponses.ok(
               "Cita actualizada correctamente",
               citaService.update(id, request)
       );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){

        citaService.delete(id);

        return ApiResponse.ApiResponses.deleted(
                "Cita eliminada correctamente"
        );
    }


}
