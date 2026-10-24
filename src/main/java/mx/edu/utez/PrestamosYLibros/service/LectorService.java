package mx.edu.utez.PrestamosYLibros.service;

import mx.edu.utez.PrestamosYLibros.dto.RequestLectorDTO;
import mx.edu.utez.PrestamosYLibros.exception.RecursoNoEncontradoException;
import mx.edu.utez.PrestamosYLibros.model.lector.Lector;
import mx.edu.utez.PrestamosYLibros.repository.LectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LectorService {

    @Autowired
    private LectorRepository lectorRepository;

    @Transactional
    public Lector crearLector(RequestLectorDTO dto) {
        Lector lector = new Lector();
        lector.setNombre(dto.getNombre());
        lector.setMembresiaPremium(dto.isMembresiaPremium());
        lector.setActivo(true);
        return lectorRepository.save(lector);
    }

    @Transactional
    public Lector rehabilitarLector(Long lectorId) {
        Lector lector = lectorRepository.findById(lectorId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El lector con id " + lectorId + " no existe"));

        lector.setActivo(true);
        return lectorRepository.save(lector);
    }
}