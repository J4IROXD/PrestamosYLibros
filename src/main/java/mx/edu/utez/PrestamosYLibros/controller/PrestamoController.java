package mx.edu.utez.PrestamosYLibros.controller;

import jakarta.validation.Valid;
import mx.edu.utez.PrestamosYLibros.dto.RequestPrestamoDTO;
import mx.edu.utez.PrestamosYLibros.model.prestamo.Prestamo;
import mx.edu.utez.PrestamosYLibros.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<Prestamo> solicitarPrestamo(@Valid @RequestBody RequestPrestamoDTO dto) {
        Prestamo nuevoPrestamo = prestamoService.solicitarPrestamo(dto);
        return new ResponseEntity<>(nuevoPrestamo, HttpStatus.CREATED);
    }
}