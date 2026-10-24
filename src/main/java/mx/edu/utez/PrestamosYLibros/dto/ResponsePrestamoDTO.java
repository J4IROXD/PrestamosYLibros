package mx.edu.utez.PrestamosYLibros.dto;

import lombok.Getter;
import lombok.Setter;
import mx.edu.utez.PrestamosYLibros.model.prestamo.EstadoPrestamo;
import mx.edu.utez.PrestamosYLibros.model.prestamo.Prestamo;

import java.time.LocalDate;

@Getter
@Setter
public class ResponsePrestamoDTO {

    private Long id;
    private Long lectorId;
    private String lectorNombre;
    private Long libroId;
    private String libroTitulo;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionMaxima;
    private EstadoPrestamo estado;

    public static ResponsePrestamoDTO desde(Prestamo prestamo) {
        ResponsePrestamoDTO dto = new ResponsePrestamoDTO();
        dto.setId(prestamo.getId());
        dto.setLectorId(prestamo.getLector().getId());
        dto.setLectorNombre(prestamo.getLector().getNombre());
        dto.setLibroId(prestamo.getLibro().getId());
        dto.setLibroTitulo(prestamo.getLibro().getTitulo());
        dto.setFechaPrestamo(prestamo.getFechaPrestamo());
        dto.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima());
        dto.setEstado(prestamo.getEstado());
        return dto;
    }
}