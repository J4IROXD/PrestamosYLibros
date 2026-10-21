package mx.edu.utez.PrestamosYLibros.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPrestamoDTO {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long lectorId;

    @NotNull(message = "El ID del libro es obligatorio")
    private Long libroId;
}
