package com.dh.Integrador.entity;

import com.dh.Integrador.utils.GsonProvider;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "odontologos")
public class Odontologo {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @NotBlank
   private String nroMatricula;
   private String apellido;
   private String nombre;

   @OneToMany(mappedBy = "odontologo", cascade = CascadeType.REMOVE)
   @JsonManagedReference(value = "odontologo-turno")
   private Set<Turno> turnoSet;

   public Odontologo(int i, String lopez, String adrian, String number) {
   }

   @Override
   public String toString() {
      return GsonProvider.getGson().toJson(this);
   }
}