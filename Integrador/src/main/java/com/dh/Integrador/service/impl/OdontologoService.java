package com.dh.Integrador.service.impl;

import com.dh.Integrador.entity.Odontologo;
import com.dh.Integrador.repository.IOdontologoRepository;
import com.dh.Integrador.service.IOdontologoService;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {
    private IOdontologoRepository odontologoRepository;
    private static final Logger logger = LogManager.getLogger(OdontologoService.class);

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        logger.info("Guardando odontólogo: {}", odontologo);
        try {
            return odontologoRepository.save(odontologo);
        } catch (Exception e) {
            logger.error("Error al guardar odontólogo: {}", odontologo, e);
            throw e;
        }
    }

    @Override
    public Optional<Odontologo> buscarPorId(Integer id) {
        logger.info("Obteniendo odontólogo con ID: {}", id);
        try {
            return Optional.ofNullable(odontologoRepository.findById(id).orElseThrow(() -> new RuntimeException("Odontólogo no encontrado")));
        } catch (Exception e) {
            logger.error("Error al obtener odontólogo con ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) {
        return odontologoRepository.findByNombreContaining(nombre);
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        logger.info("Eliminando odontólogo con ID: {}", id);
        try {
            odontologoRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error al eliminar odontólogo con ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<Odontologo> listarTodos() {
        logger.info("Obteniendo todos los odontólogos");
        try {
            return odontologoRepository.findAll();
        } catch (Exception e) {
            logger.error("Error al obtener todos los odontólogos", e);
            throw e;
        }
    }

    @Override
    public Odontologo actualizarOdontologo(Odontologo odontologoActualizado) {
        logger.info("Actualizando odontólogo: {}", odontologoActualizado);
        try {
            if (!odontologoRepository.existsById(odontologoActualizado.getId())) {
                throw new RuntimeException("Odontólogo no encontrado");
            }
            return odontologoRepository.save(odontologoActualizado);
        } catch (Exception e) {
            logger.error("Error al actualizar odontólogo: {}", odontologoActualizado, e);
            throw e;
        }
    }


}