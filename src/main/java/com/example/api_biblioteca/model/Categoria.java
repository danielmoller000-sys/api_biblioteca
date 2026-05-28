package com.example.api_biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "categorias")
@Data
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    // Relación N a M (Módulo C)
    @ManyToMany(mappedBy = "categorias")
    @JsonIgnore
    @ToString.Exclude
    private List<Libro> libros;
}

