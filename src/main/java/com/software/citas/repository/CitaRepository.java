package com.software.citas.repository;

import com.software.citas.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita>  findByDocumento(String documento);
    List<Cita> findByNombres(String nombres);
    List<Cita>  findByApellidos(String apellidos);
    List<Cita>  findByTipoCita(String tipoCita);
}
