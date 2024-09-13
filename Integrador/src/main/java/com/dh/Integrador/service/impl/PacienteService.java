package com.dh.Integrador.service.impl;

import com.dh.Integrador.entity.Paciente;
import com.dh.Integrador.repository.IPacienteRepository;
import com.dh.Integrador.service.IPacienteService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {

    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public Optional<Paciente> buscarPorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    public void modificarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    @Override
    public void eliminarPaciente(Integer id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre) {
        return List.of();
    }

    @Override
    public List<Paciente> buscarPorUnaParteApellido(String parte) {
        return List.of();
    }
    @Override
    public List<Paciente> buscarPorFechaIngreso(LocalDate fechaIngreso) {
        return pacienteRepository.findByFechaIngreso(fechaIngreso);
    }
    @Override
    public List<Paciente> buscarPorDomicilioLocalidad(String localidad) {
        return pacienteRepository.findByDomicilioLocalidad(localidad);
    }

    @Override
    public Optional<Paciente> buscarPorDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }
}