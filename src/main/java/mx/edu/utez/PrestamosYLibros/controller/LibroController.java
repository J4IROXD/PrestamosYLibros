package mx.edu.utez.PrestamosYLibros.controller;

import jakarta.validation.Valid;
import mx.edu.utez.PrestamosYLibros.dto.RequestLibroDTO;
import mx.edu.utez.PrestamosYLibros.model.libro.Libro;
import mx.edu.utez.PrestamosYLibros.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @PostMapping
    public ResponseEntity<Libro> crearLibro(@Valid @RequestBody RequestLibroDTO dto) {
        return new ResponseEntity<>(libroService.crearLibro(dto), HttpStatus.CREATED);
    }
}