package mx.edu.utez.PrestamosYLibros.service;

import mx.edu.utez.PrestamosYLibros.dto.RequestPrestamoDTO;
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

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LectorRepository lectorRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Transactional
    public Prestamo solicitarPrestamo(RequestPrestamoDTO dto){
        Lector lector = lectorRepository.findById(dto.getLectorId())
                .orElseThrow(() -> new RuntimeException("El lector no existe"));

        if (!lector.isActivo()){
            throw new RuntimeException("El lector no esta activo");
        }

        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new RuntimeException("El libro no existe"));


        if (libro.getEjemplaresDisponibles() < 1){
            throw new RuntimeException("No hay libros disponibles por el momento");
        }

        List<EstadoPrestamo> estadosActivos = List.of(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO);
        List<Prestamo> prestamosActivos = prestamoRepository.findByLectorIdAndEstadoIn(lector.getId(), estadosActivos);

        int limiteMaximo = lector.isPremium() ? 5 : 3;

        if(prestamosActivos.size() >= limiteMaximo){
            throw new RuntimeException("El lector ha llegado al limite máximo de prestamos (" + limiteMaximo + ")");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevoluvcionMaxima(LocalDate.now().plusDays(14));
        prestamo.setEstado(EstadoPrestamo.PRESTADO);

        return prestamoRepository.save(prestamo);


    }
}
