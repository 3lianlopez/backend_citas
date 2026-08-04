package com.software.citas.dto.response;


import java.time.LocalDate;
import java.time.LocalTime;

public class CitaResponse {

    private Long id;

    private String tipo_cita;

    private LocalDate fecha;

    private LocalTime hora;

    public CitaResponse(){}

    public CitaResponse(Long id, String tipo_cita, LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.tipo_cita = tipo_cita;
        this.fecha = fecha;
        this.hora = hora;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo_cita() {
        return tipo_cita;
    }

    public void setTipo_cita(String tipo_cita) {
        this.tipo_cita = tipo_cita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
