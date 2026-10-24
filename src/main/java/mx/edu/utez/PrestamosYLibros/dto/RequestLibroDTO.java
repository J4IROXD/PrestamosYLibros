package mx.edu.utez.PrestamosYLibros.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLibroDTO {

    @NotBlank(message = "El título del libro es obligatorio")
    private String titulo;

    @NotNull(message = "Los ejemplares disponibles son obligatorios")
    @Min(value = 0, message = "Los ejemplares disponibles no pueden ser negativos")
    private Integer ejemplaresDisponibles;
}