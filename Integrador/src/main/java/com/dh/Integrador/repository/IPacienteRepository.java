package com.dh.Integrador.repository;

import com.dh.Integrador.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Integer>  {
    @Query("SELECT p FROM Paciente p WHERE p.fechaIngreso = :fechaIngreso")
    List<Paciente> findByFechaIngreso(@Param("fechaIngreso") LocalDate fechaIngreso);

    @Query("SELECT p FROM Paciente p JOIN p.domicilio d WHERE d.localidad = :localidad")
    List<Paciente> findByDomicilioLocalidad(@Param("localidad") String localidad);

    @Query("SELECT p FROM Paciente p WHERE p.dni = :dni")
    Optional<Paciente> findByDni(@Param("dni") String dni);
}
