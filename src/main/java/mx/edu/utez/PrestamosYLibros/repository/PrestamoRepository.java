package mx.edu.utez.PrestamosYLibros.repository;

import mx.edu.utez.PrestamosYLibros.model.prestamo.EstadoPrestamo;
import mx.edu.utez.PrestamosYLibros.model.prestamo.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByLectorIdAndEstadoIn(Long lectorId, List<EstadoPrestamo> estados);
}
