package com.example.api_biblioteca.Service;

import com.example.api_biblioteca.model.Libro;
import com.example.api_biblioteca.Repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;

    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    public Optional<Libro> findById(Long id) {
        return libroRepository.findById(id); // Cumple el uso correcto de Optional (Núcleo)
    }

    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    public void deleteById(Long id) {
        libroRepository.deleteById(id);
    }

    // Módulo B
    public List<Libro> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty()) {
            return libroRepository.findAll();
        }
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // Módulo C
    public Long contarPorCategoria(Long idCategoria) {
        return libroRepository.contarLibrosPorCategoria(idCategoria);
    }
}

