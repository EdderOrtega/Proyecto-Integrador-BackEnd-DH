package com.dh.Integrador.service;

import com.dh.Integrador.entity.Odontologo;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {
    Odontologo guardarOdontologo(Odontologo odontologo);
    Optional<Odontologo> buscarPorId(Integer id);
    List<Odontologo> buscarPorNombre(String nombre);
    void eliminarOdontologo(Integer id);
    List<Odontologo> listarTodos();
}
