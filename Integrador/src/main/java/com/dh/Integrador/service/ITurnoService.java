package com.dh.Integrador.service;

import com.dh.Integrador.dto.request.TurnoModifyDto;
import com.dh.Integrador.dto.request.TurnoRequestDto;
import com.dh.Integrador.dto.response.TurnoResponseDto;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Optional;

public interface ITurnoService {
    TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto) throws BadRequestException;

    Optional<TurnoResponseDto> buscarPorId(Integer id);
    List<TurnoResponseDto> buscarTodos();

    void modificarTurnos(TurnoModifyDto turnoModifyDto);

    void eliminarTurno(Integer id);
    Optional<TurnoResponseDto> buscarTurnosPorPaciente(String pacienteApellido);
}