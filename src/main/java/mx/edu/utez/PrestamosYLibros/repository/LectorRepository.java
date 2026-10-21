package mx.edu.utez.PrestamosYLibros.repository;

import mx.edu.utez.PrestamosYLibros.model.lector.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
}
