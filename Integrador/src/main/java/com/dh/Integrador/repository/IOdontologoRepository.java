package com.dh.Integrador.repository;

import com.dh.Integrador.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {

    List<Odontologo> findByNombreContaining(String nombre);

}
