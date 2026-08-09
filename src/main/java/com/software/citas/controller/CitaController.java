package com.software.citas.controller;

import com.software.citas.dto.response.ApiResponse;
import com.software.citas.dto.response.CitaResponse;
import com.software.citas.dto.request.CitaDTO;
import com.software.citas.dto.response.ResponseFactory;
import com.software.citas.service.CitaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private static final Logger log = LoggerFactory.getLogger(CitaController.class);
    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CitaResponse>>> findAll(){
        return ApiResponse.ApiResponses.ok("Se han encontrado citas correctamente.", citaService.findAll());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CitaResponse>> create( @RequestBody CitaDTO request) {
        log.debug("REST request to save Cita : {}", request);
        return ApiResponse.ApiResponses.created(
                "Cita creada correctamente.",
                citaService.create(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaResponse>> findById(@PathVariable Long id){

        return ApiResponse.ApiResponses.ok(
                "Cita encontrada correctamente.",
                citaService.findById(id)
        );

    }

    @GetMapping("/cliente/nombres/{nombres}")
    public ResponseEntity<ApiResponse<List<CitaResponse>>> findByNombres(@PathVariable String nombres){
        return ApiResponse.ApiResponses.ok(
                "Citas encontrada correctamente",
                citaService.findByNombre(nombres)
        );
    }

    @GetMapping("/cliente/apellidos/{apellidos}")
    public ResponseEntity<ApiResponse<List<CitaResponse>>> findByApellidos(@PathVariable String apellidos){
        return ApiResponse.ApiResponses.ok(
                "Cita encontrada correctamente",
                citaService.findByApellido(apellidos)
        );
    }

    @GetMapping("/cliente/documento/{documento}")
    public ResponseEntity<ApiResponse<List<CitaResponse>>> findByDocumento(@PathVariable String documento){
        return ApiResponse.ApiResponses.ok(
                "Cita encontrada correctamente",
                citaService.findByDocumento(documento)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaResponse>> update(
            @PathVariable Long id,
            @RequestBody CitaDTO request){

       return ApiResponse.ApiResponses.ok(
               "Cita actualizada correctamente",
               citaService.update(id, request)
       );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){

        citaService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<ApiResponse<CitaResponse>> confirmar(
            @PathVariable Long id) {

        return ResponseFactory.ok(
                citaService.confirmar(id)
        );
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<ApiResponse<CitaResponse>> iniciar(
            @PathVariable Long id) {

        return ResponseFactory.ok(
                citaService.iniciar(id)
        );
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<ApiResponse<CitaResponse>> finalizar(
            @PathVariable Long id) {

        return ResponseFactory.ok(
                citaService.finalizar(id)
        );
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ApiResponse<CitaResponse>> cancelar(
            @PathVariable Long id) {

        return ResponseFactory.ok(
                citaService.cancelar(id)
        );
    }


}
