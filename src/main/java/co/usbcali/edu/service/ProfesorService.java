package co.usbcali.edu.service;

import co.usbcali.edu.domain.Profesor;
import co.usbcali.edu.repository.ProfesorRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Profesor}.
 */
@Service
@Transactional
public class ProfesorService {

    private final Logger log = LoggerFactory.getLogger(ProfesorService.class);

    private final ProfesorRepository profesorRepository;

    public ProfesorService(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    /**
     * Save a profesor.
     *
     * @param profesor the entity to save.
     * @return the persisted entity.
     */
    public Mono<Profesor> save(Profesor profesor) {
        log.debug("Request to save Profesor : {}", profesor);
        return profesorRepository.save(profesor);
    }

    /**
     * Update a profesor.
     *
     * @param profesor the entity to save.
     * @return the persisted entity.
     */
    public Mono<Profesor> update(Profesor profesor) {
        log.debug("Request to update Profesor : {}", profesor);
        return profesorRepository.save(profesor);
    }

    /**
     * Partially update a profesor.
     *
     * @param profesor the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<Profesor> partialUpdate(Profesor profesor) {
        log.debug("Request to partially update Profesor : {}", profesor);

        return profesorRepository
            .findById(profesor.getId())
            .map(existingProfesor -> {
                if (profesor.getNombre() != null) {
                    existingProfesor.setNombre(profesor.getNombre());
                }
                if (profesor.getApellido() != null) {
                    existingProfesor.setApellido(profesor.getApellido());
                }
                if (profesor.getCodigo() != null) {
                    existingProfesor.setCodigo(profesor.getCodigo());
                }
                if (profesor.getCorreo() != null) {
                    existingProfesor.setCorreo(profesor.getCorreo());
                }

                return existingProfesor;
            })
            .flatMap(profesorRepository::save);
    }

    /**
     * Get all the profesors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<Profesor> findAll() {
        log.debug("Request to get all Profesors");
        return profesorRepository.findAll();
    }

    /**
     * Returns the number of profesors available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return profesorRepository.count();
    }

    /**
     * Get one profesor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<Profesor> findOne(Long id) {
        log.debug("Request to get Profesor : {}", id);
        return profesorRepository.findById(id);
    }

    /**
     * Delete the profesor by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Profesor : {}", id);
        return profesorRepository.deleteById(id);
    }
}
