package mx.edu.utez.PrestamosYLibros.service;

import mx.edu.utez.PrestamosYLibros.dto.RequestLibroDTO;
import mx.edu.utez.PrestamosYLibros.model.libro.Libro;
import mx.edu.utez.PrestamosYLibros.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Transactional
    public Libro crearLibro(RequestLibroDTO dto) {
        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setEjemplaresDisponibles(dto.getEjemplaresDisponibles());
        return libroRepository.save(libro);
    }
}