package com.example.api_biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.util.List;
@Entity
@Table(name = "autores")
@Data
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del autor es obligatorio")
    private String nombre;

    private String nacionalidad;

    // Relación 1 a N (Módulo A)
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonIgnore // Evita el bucle infinito al serializar a JSON
    @ToString.Exclude
    private List<Libro> libros;
}
