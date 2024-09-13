package com.dh.Integrador.controller;

import com.dh.Integrador.entity.Odontologo;
import com.dh.Integrador.repository.IOdontologoRepository;
import com.dh.Integrador.service.IOdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {

    private final IOdontologoService odontologoService;

    @Autowired
    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Odontologo> agregarOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Odontologo> buscarPorId(@PathVariable Integer id){
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(id);
        if(odontologo.isPresent()){
            return ResponseEntity.ok(odontologo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarPorNombre")
    public ResponseEntity<List<Odontologo>> buscarPorNombre(@RequestParam String nombre) {
        List<Odontologo> odontologos = odontologoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(odontologos);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Odontologo> actualizarOdontologo(@RequestBody Odontologo odontologo) {
        if (odontologo.getId() != null && odontologoService.buscarPorId(odontologo.getId()).isPresent()) {
            Odontologo actualizado = odontologoService.guardarOdontologo(odontologo);
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Integer id) {
        try {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok().body("Odontólogo eliminado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el odontólogo.");
        }
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Odontologo>> listarTodos() {
        List<Odontologo> odontologos = odontologoService.listarTodos();
        return ResponseEntity.ok(odontologos);
    }

}
