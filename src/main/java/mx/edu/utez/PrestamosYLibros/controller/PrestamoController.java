package mx.edu.utez.PrestamosYLibros.controller;

import jakarta.validation.Valid;
import mx.edu.utez.PrestamosYLibros.dto.RequestPrestamoDTO;
import mx.edu.utez.PrestamosYLibros.dto.ResponsePrestamoDTO;
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
    public ResponseEntity<ResponsePrestamoDTO> solicitarPrestamo(@Valid @RequestBody RequestPrestamoDTO dto) {
        ResponsePrestamoDTO respuesta = ResponsePrestamoDTO.desde(prestamoService.solicitarPrestamo(dto));
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<ResponsePrestamoDTO> devolverLibro(@PathVariable Long id) {
        return ResponseEntity.ok(ResponsePrestamoDTO.desde(prestamoService.devolverLibro(id)));
    }

    @PutMapping("/{id}/renovar")
    public ResponseEntity<ResponsePrestamoDTO> renovarPrestamo(@PathVariable Long id) {
        return ResponseEntity.ok(ResponsePrestamoDTO.desde(prestamoService.renovarPrestamo(id)));
    }
}