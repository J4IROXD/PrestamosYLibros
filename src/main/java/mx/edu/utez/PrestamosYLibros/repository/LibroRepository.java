package mx.edu.utez.PrestamosYLibros.repository;

import mx.edu.utez.PrestamosYLibros.model.libro.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}
