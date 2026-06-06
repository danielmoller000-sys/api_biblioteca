package com.example.api_biblioteca.Service;

import com.example.api_biblioteca.model.Autor;
import com.example.api_biblioteca.Repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;

    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    public Optional<Autor> findById(Long id) {
        return autorRepository.findById(id);
    }

    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }

    public void deleteById(Long id) {
        autorRepository.deleteById(id);
    }
    public List<Autor> buscarPorNacionalidad(String nacionalidad) {
        if (nacionalidad == null || nacionalidad.isEmpty()) {
            return autorRepository.findAll();
        }
        return autorRepository.findByNacionalidadContainingIgnoreCase(nacionalidad);
    }
}
