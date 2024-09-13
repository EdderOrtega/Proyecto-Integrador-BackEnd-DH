package com.dh.Integrador.controller;

import com.dh.Integrador.entity.Paciente;
import com.dh.Integrador.service.impl.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
    public class VistaController {

        private final PacienteService pacienteService;

        public VistaController(PacienteService pacienteService) {
            this.pacienteService = pacienteService;
        }

        @GetMapping("/index")
        public String buscarPaciente(Model model, @RequestParam Integer id){
            Optional<Paciente> paciente = pacienteService.buscarPorId(id);
            if (paciente.isPresent()) {
                model.addAttribute("nombre", paciente.get().getNombre());
                model.addAttribute("apellido", paciente.get().getApellido());
                return "vista/paciente";
            } else {
                return "error";
            }
        }
    }
