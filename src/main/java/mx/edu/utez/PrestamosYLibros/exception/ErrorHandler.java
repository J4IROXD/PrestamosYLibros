package mx.edu.utez.PrestamosYLibros.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validar(
            MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            errores.put(err.getField(), err.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errores);

    }
    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<Map<String, String>> manejarBadRequest(
            ReglaNegocioException ex) {

        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", ex.getMessage());

        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarNotFound(
            RecursoNoEncontradoException ex) {

        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", ex.getMessage());

        return ResponseEntity.status(404).body(errores);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> manejarTipoInvalido(
            MethodArgumentTypeMismatchException ex) {

        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", "El valor de '" + ex.getName() + "' no tiene un formato válido");

        return ResponseEntity.badRequest().body(errores);
    }
}
