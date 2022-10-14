package co.usbcali.edu.service;

import co.usbcali.edu.domain.Estudiante;
import co.usbcali.edu.repository.EstudianteRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Estudiante}.
 */
@Service
@Transactional
public class EstudianteService {

    private final Logger log = LoggerFactory.getLogger(EstudianteService.class);

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    /**
     * Save a estudiante.
     *
     * @param estudiante the entity to save.
     * @return the persisted entity.
     */
    public Mono<Estudiante> save(Estudiante estudiante) {
        log.debug("Request to save Estudiante : {}", estudiante);
        return estudianteRepository.save(estudiante);
    }

    /**
     * Update a estudiante.
     *
     * @param estudiante the entity to save.
     * @return the persisted entity.
     */
    public Mono<Estudiante> update(Estudiante estudiante) {
        log.debug("Request to update Estudiante : {}", estudiante);
        return estudianteRepository.save(estudiante);
    }

    /**
     * Partially update a estudiante.
     *
     * @param estudiante the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<Estudiante> partialUpdate(Estudiante estudiante) {
        log.debug("Request to partially update Estudiante : {}", estudiante);

        return estudianteRepository
            .findById(estudiante.getId())
            .map(existingEstudiante -> {
                if (estudiante.getNombre() != null) {
                    existingEstudiante.setNombre(estudiante.getNombre());
                }
                if (estudiante.getApellido() != null) {
                    existingEstudiante.setApellido(estudiante.getApellido());
                }
                if (estudiante.getCodigo() != null) {
                    existingEstudiante.setCodigo(estudiante.getCodigo());
                }
                if (estudiante.getCorreo() != null) {
                    existingEstudiante.setCorreo(estudiante.getCorreo());
                }

                return existingEstudiante;
            })
            .flatMap(estudianteRepository::save);
    }

    /**
     * Get all the estudiantes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<Estudiante> findAll() {
        log.debug("Request to get all Estudiantes");
        return estudianteRepository.findAll();
    }

    /**
     * Returns the number of estudiantes available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return estudianteRepository.count();
    }

    /**
     * Get one estudiante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<Estudiante> findOne(Long id) {
        log.debug("Request to get Estudiante : {}", id);
        return estudianteRepository.findById(id);
    }

    /**
     * Delete the estudiante by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Estudiante : {}", id);
        return estudianteRepository.deleteById(id);
    }
}
