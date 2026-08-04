package com.software.citas.dto.request;


import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateCitaRequest {

    private String tipoCita;

    private LocalDate fecha;

    private LocalTime hora;

    public UpdateCitaRequest() {
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
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