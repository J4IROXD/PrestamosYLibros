package mx.edu.utez.PrestamosYLibros.service;

import mx.edu.utez.PrestamosYLibros.dto.RequestPrestamoDTO;
import mx.edu.utez.PrestamosYLibros.exception.RecursoNoEncontradoException;
import mx.edu.utez.PrestamosYLibros.exception.ReglaNegocioException;
import mx.edu.utez.PrestamosYLibros.model.lector.Lector;
import mx.edu.utez.PrestamosYLibros.model.libro.Libro;
import mx.edu.utez.PrestamosYLibros.model.prestamo.EstadoPrestamo;
import mx.edu.utez.PrestamosYLibros.model.prestamo.Prestamo;
import mx.edu.utez.PrestamosYLibros.repository.LectorRepository;
import mx.edu.utez.PrestamosYLibros.repository.LibroRepository;
import mx.edu.utez.PrestamosYLibros.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    private static final List<EstadoPrestamo> ESTADOS_ACTIVOS =
            List.of(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO);

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LectorRepository lectorRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Transactional
    public Prestamo solicitarPrestamo(RequestPrestamoDTO dto) {
        Lector lector = lectorRepository.findById(dto.getLectorId())
                .orElseThrow(() -> new RecursoNoEncontradoException("El lector con id " + dto.getLectorId() + " no existe"));

        if (!lector.isActivo()) {
            throw new ReglaNegocioException("El lector no está activo");
        }

        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new RecursoNoEncontradoException("El libro con id " + dto.getLibroId() + " no existe"));

        if (libro.getEjemplaresDisponibles() < 1) {
            throw new ReglaNegocioException("No hay ejemplares disponibles de este libro por el momento");
        }

        List<Prestamo> prestamosActivos =
                prestamoRepository.findByLectorIdAndEstadoIn(lector.getId(), ESTADOS_ACTIVOS);

        int limiteMaximo = lector.isMembresiaPremium() ? 5 : 3;

        if (prestamosActivos.size() >= limiteMaximo) {
            throw new ReglaNegocioException("El lector ha llegado al límite máximo de préstamos activos (" + limiteMaximo + ")");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        LocalDate hoy = LocalDate.now();

        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(hoy);
        prestamo.setFechaDevolucionMaxima(hoy.plusDays(14));
        prestamo.setEstado(EstadoPrestamo.PRESTADO);

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo devolverLibro(Long prestamoId) {
        Prestamo prestamo = buscarPrestamo(prestamoId);

        if (!ESTADOS_ACTIVOS.contains(prestamo.getEstado())) {
            throw new ReglaNegocioException("El préstamo no está activo, su estado actual es " + prestamo.getEstado());
        }

        boolean entregaTardia = LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima());

        prestamo.setEstado(EstadoPrestamo.DEVUELTO);

        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);

        if (entregaTardia) {
            Lector lector = prestamo.getLector();
            lector.setActivo(false);
            lectorRepository.save(lector);
        }

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo renovarPrestamo(Long prestamoId) {
        Prestamo prestamo = buscarPrestamo(prestamoId);

        if (prestamo.getEstado() == EstadoPrestamo.VENCIDO || prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new ReglaNegocioException("No se puede renovar un préstamo en estado " + prestamo.getEstado());
        }

        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(7));

        return prestamoRepository.save(prestamo);
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamosActivosLector(Long lectorId) {
        if (!lectorRepository.existsById(lectorId)) {
            throw new RecursoNoEncontradoException("El lector con id " + lectorId + " no existe");
        }
        return prestamoRepository.findByLectorIdAndEstadoIn(lectorId, ESTADOS_ACTIVOS);
    }

    private Prestamo buscarPrestamo(Long prestamoId) {
        return prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El préstamo con id " + prestamoId + " no existe"));
    }
}