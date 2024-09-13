package com.dh.Integrador.controller;

import com.dh.Integrador.entity.Paciente;
import com.dh.Integrador.service.IPacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Paciente> guardarPaciente(@Valid @RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }


    @PutMapping("/modificar")
    public ResponseEntity<String>  modificarPaciente(@RequestBody Paciente paciente){
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPorId(paciente.getId());
        if(pacienteEncontrado.isPresent()){
            pacienteService.modificarPaciente(paciente);
            String jsonResponse = "{\"mensaje\": \"El paciente fue modificado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Integer id){
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("{\"mensaje\": \"El paciente fue eliminado\"}");
    }


    @GetMapping("/buscar/{id}")
    public ResponseEntity<Paciente>  buscarPorId(@PathVariable Integer id){
        Optional<Paciente>  pacienteEncontrado = pacienteService.buscarPorId(id);
        if(pacienteEncontrado.isPresent()) {
            return ResponseEntity.ok(pacienteEncontrado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/buscartodos")
    public ResponseEntity<List<Paciente>>  buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @GetMapping("/buscarApellidoNombre/{apellido}/{nombre}")
    public ResponseEntity<List<Paciente>> buscarApellido(@PathVariable String apellido, @PathVariable String nombre){
        return ResponseEntity.ok(pacienteService.buscarPorApellidoyNombre(apellido, nombre));
    }

    @GetMapping("/buscarApellido/{parte}")
    public ResponseEntity<List<Paciente>> buscarParteApellido(@PathVariable String parte){
        return ResponseEntity.ok(pacienteService.buscarPorUnaParteApellido(parte));
    }

    //agregados para paciente
    @GetMapping("/buscarFechaIngreso/{fechaIngreso}")
    public ResponseEntity<List<Paciente>> buscarPorFechaIngreso(@PathVariable LocalDate fechaIngreso) {
        return ResponseEntity.ok(pacienteService.buscarPorFechaIngreso(fechaIngreso));
    }
    @GetMapping("/buscarDomicilioLocalidad/{localidad}")
    public ResponseEntity<List<Paciente>> buscarPorDomicilioLocalidad(@PathVariable String localidad) {
        return ResponseEntity.ok(pacienteService.buscarPorDomicilioLocalidad(localidad));
    }

    @GetMapping("/buscarDni/{dni}")
    public ResponseEntity<Optional<Paciente>> buscarPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(pacienteService.buscarPorDni(dni));
    }
}