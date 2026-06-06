package com.example.api_biblioteca.controller;

import com.example.api_biblioteca.model.Autor;
import com.example.api_biblioteca.Service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @GetMapping
    public ResponseEntity<List<Autor>> getAll() {
        return ResponseEntity.ok(autorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> getById(@PathVariable Long id) {
        return autorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Autor> create(@Valid @RequestBody Autor autor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.save(autor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autor> update(@PathVariable Long id, @Valid @RequestBody Autor autorDetails) {
        return autorService.findById(id).map(autorExistente -> {
            autorExistente.setNombre(autorDetails.getNombre());
            autorExistente.setNacionalidad(autorDetails.getNacionalidad());
            return ResponseEntity.ok(autorService.save(autorExistente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (autorService.findById(id).isPresent()) {
            autorService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/buscar")
    public ResponseEntity<List<Autor>> buscar(@RequestParam(required = false) String nacionalidad) {
        return ResponseEntity.ok(autorService.buscarPorNacionalidad(nacionalidad));
    }
}