package mx.edu.utez.PrestamosYLibros.service;

import mx.edu.utez.PrestamosYLibros.dto.RequestPrestamoDTO;
import mx.edu.utez.PrestamosYLibros.model.lector.Lector;
import mx.edu.utez.PrestamosYLibros.model.libro.Libro;
import mx.edu.utez.PrestamosYLibros.model.prestamo.Prestamo;
import mx.edu.utez.PrestamosYLibros.repository.LectorRepository;
import mx.edu.utez.PrestamosYLibros.repository.LibroRepository;
import mx.edu.utez.PrestamosYLibros.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return null;
        
    }
}
