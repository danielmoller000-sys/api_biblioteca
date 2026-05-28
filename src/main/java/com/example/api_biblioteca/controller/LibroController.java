package com.example.api_biblioteca.controller;


import com.example.api_biblioteca.model.Libro;
import com.example.api_biblioteca.Service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
@RestController
@RequestMapping("/api/v1/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    // GET /api/v1/libros
    @GetMapping
    public ResponseEntity<List<Libro>> getAll() {
        return ResponseEntity.ok(libroService.findAll());
    }

    // GET /api/v1/libros/{id} - Uso exigido de Optional y map
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getById(@PathVariable Long id) {
        return libroService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/v1/libros
    @PostMapping
    public ResponseEntity<Libro> create(@Valid @RequestBody Libro libro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.save(libro));
    }

    // PUT /api/v1/libros/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Libro> update(@PathVariable Long id, @Valid @RequestBody Libro libroDetails) {
        return libroService.findById(id).map(libroExistente -> {
            libroExistente.setTitulo(libroDetails.getTitulo());
            libroExistente.setIsbn(libroDetails.getIsbn());
            libroExistente.setAutor(libroDetails.getAutor());
            libroExistente.setCategorias(libroDetails.getCategorias());
            return ResponseEntity.ok(libroService.save(libroExistente));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/v1/libros/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (libroService.findById(id).isPresent()) {
            libroService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // MÓDULO B: Query Params (?titulo=Java)
    @GetMapping("/buscar")
    public ResponseEntity<List<Libro>> buscar(@RequestParam(required = false) String titulo) {
        return ResponseEntity.ok(libroService.buscarPorTitulo(titulo));
    }

    // MÓDULO C: Uso de JPQL (@Query)
    @GetMapping("/count/categoria/{id}")
    public ResponseEntity<Long> contarPorCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.contarPorCategoria(id));
    }
}

