package mx.edu.utez.PrestamosYLibros.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLectorDTO {

    @NotBlank(message = "El nombre del lector es obligatorio")
    private String nombre;

    private boolean membresiaPremium;
}