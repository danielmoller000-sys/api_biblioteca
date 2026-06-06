package com.example.api_biblioteca.Repository;

import com.example.api_biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Método derivado (Módulo B)
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    // Método derivado que navega la relación con Autor (Módulo A)
    List<Libro> findByAutorId(Long autorId);

    // Consulta JPQL personalizada (Módulo C)
    @Query("SELECT COUNT(l) FROM Libro l JOIN l.categorias c WHERE c.id = :categoriaId")
    Long contarLibrosPorCategoria(@Param("categoriaId") Long categoriaId);
}

