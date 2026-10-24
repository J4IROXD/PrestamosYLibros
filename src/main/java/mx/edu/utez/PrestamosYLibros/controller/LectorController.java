package mx.edu.utez.PrestamosYLibros.controller;

import jakarta.validation.Valid;
import mx.edu.utez.PrestamosYLibros.dto.RequestLectorDTO;
import mx.edu.utez.PrestamosYLibros.dto.ResponsePrestamoDTO;
import mx.edu.utez.PrestamosYLibros.model.lector.Lector;
import mx.edu.utez.PrestamosYLibros.service.LectorService;
import mx.edu.utez.PrestamosYLibros.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectores")
public class LectorController {

    @Autowired
    private LectorService lectorService;

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<Lector> crearLector(@Valid @RequestBody RequestLectorDTO dto) {
        return new ResponseEntity<>(lectorService.crearLector(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/rehabilitar")
    public ResponseEntity<Lector> rehabilitarLector(@PathVariable Long id) {
        return ResponseEntity.ok(lectorService.rehabilitarLector(id));
    }

    @GetMapping("/{id}/prestamos-activos")
    public ResponseEntity<List<ResponsePrestamoDTO>> listarPrestamosActivosLector(@PathVariable Long id) {
        List<ResponsePrestamoDTO> respuesta = prestamoService.listarPrestamosActivosLector(id).stream()
                .map(ResponsePrestamoDTO::desde)
                .toList();
        return ResponseEntity.ok(respuesta);
    }
}