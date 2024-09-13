package com.dh.Integrador.service;

import com.dh.Integrador.entity.Paciente;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    Paciente guardarPaciente(Paciente paciente);

    Optional<Paciente> buscarPorId(Integer id);
    List<Paciente> buscarTodos();

    void modificarPaciente(Paciente paciente);

    void eliminarPaciente(Integer id);

    List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre);

    List<Paciente> buscarPorUnaParteApellido(String parte);

    List<Paciente> buscarPorFechaIngreso(LocalDate fechaIngreso);

    List<Paciente> buscarPorDomicilioLocalidad(String localidad);

    Optional<Paciente> buscarPorDni(String dni);
}