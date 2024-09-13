package com.dh.Integrador.service.impl;

import com.dh.Integrador.dto.request.TurnoModifyDto;
import com.dh.Integrador.dto.request.TurnoRequestDto;
import com.dh.Integrador.dto.response.OdontologoResponseDto;
import com.dh.Integrador.dto.response.PacienteResponseDto;
import com.dh.Integrador.dto.response.TurnoResponseDto;
import com.dh.Integrador.entity.Odontologo;
import com.dh.Integrador.entity.Paciente;
import com.dh.Integrador.entity.Turno;
import com.dh.Integrador.exception.ResourceNotFoundException;
import com.dh.Integrador.repository.ITurnoRepository;
import com.dh.Integrador.service.IOdontologoService;
import com.dh.Integrador.service.IPacienteService;
import com.dh.Integrador.service.ITurnoService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    private static final Logger logger = LoggerFactory.getLogger(TurnoService.class);
    private final ITurnoRepository turnoRepository;
    private final IPacienteService pacienteService;
    private final IOdontologoService odontologoService;
    @Autowired
    private ModelMapper modelMapper;

    public TurnoService(ITurnoRepository turnoRepository, IPacienteService pacienteService, IOdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto) throws BadRequestException {
        try{
            Optional<Paciente> paciente = pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
            Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());
            Turno turno = new Turno();
            Turno turnoDesdeBD = null;
            TurnoResponseDto turnoResponseDto = null;
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));

            turnoDesdeBD = turnoRepository.save(turno);


            turnoResponseDto = convertirTurnoEnResponse(turnoDesdeBD);
            return turnoResponseDto;
        } catch (ResourceNotFoundException e){
            throw new BadRequestException("Paciente u odontologo no existen en la base de datos");
        }

    }

    @Override
    public Optional<TurnoResponseDto> buscarPorId(Integer id) {
        Optional<Turno> turno = turnoRepository.findById(id);
        if(turno.isPresent()){
            TurnoResponseDto turnoRespuesta = convertirTurnoEnResponse(turno.get());
            return Optional.of(turnoRespuesta);
        } else {
            throw new ResourceNotFoundException("El turno no fue encontrado");
        }
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnosDesdeBD = turnoRepository.findAll();
        List<TurnoResponseDto> turnosRespuesta = new ArrayList<>();
        for(Turno t: turnosDesdeBD){
            TurnoResponseDto turnoRespuesta =convertirTurnoEnResponse(t);
            logger.info("turno "+ turnoRespuesta);
            turnosRespuesta.add(turnoRespuesta);
        }
        return turnosRespuesta;
    }

    @Override
    public void modificarTurnos(TurnoModifyDto turnoModifyDto) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoModifyDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoModifyDto.getOdontologo_id());
        if(paciente.isPresent() && odontologo.isPresent()){
            Turno turno = new Turno(
                    turnoModifyDto.getId(),
                    paciente.get(), odontologo.get(), LocalDate.parse(turnoModifyDto.getFecha())
            );
            turnoRepository.save(turno);
        }
    }


    @Override
    public void eliminarTurno(Integer id){
        Optional<TurnoResponseDto> turnoEncontrado = buscarPorId(id);
        turnoRepository.deleteById(id);
    }

    @Override
    public Optional<TurnoResponseDto> buscarTurnosPorPaciente(String pacienteApellido) {
        Optional<Turno> turno = turnoRepository.buscarPorApellidoPaciente(pacienteApellido);
        TurnoResponseDto turnoParaResponder = null;
        if(turno.isPresent()) {
            turnoParaResponder = convertirTurnoEnResponse(turno.get());
        }
        return Optional.ofNullable(turnoParaResponder);
    }

    private TurnoResponseDto obtenerTurnoResponse(Turno turnoDesdeBD){
        OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto(
                turnoDesdeBD.getOdontologo().getId(), turnoDesdeBD.getOdontologo().getNroMatricula(),
                turnoDesdeBD.getOdontologo().getApellido(), turnoDesdeBD.getOdontologo().getNombre()
        );
        PacienteResponseDto pacienteResponseDto = new PacienteResponseDto(
                turnoDesdeBD.getPaciente().getId(), turnoDesdeBD.getPaciente().getApellido(),
                turnoDesdeBD.getPaciente().getNombre(), turnoDesdeBD.getPaciente().getDni()
        );
        return new TurnoResponseDto(
                turnoDesdeBD.getId(),
                pacienteResponseDto, odontologoResponseDto,
                turnoDesdeBD.getFecha().toString()
        );
    }

    private TurnoResponseDto convertirTurnoEnResponse(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        return turnoResponseDto;
    }


}