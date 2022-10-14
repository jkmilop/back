package co.usbcali.edu.service;

import co.usbcali.edu.domain.Calificacion;
import co.usbcali.edu.repository.CalificacionRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Calificacion}.
 */
@Service
@Transactional
public class CalificacionService {

    private final Logger log = LoggerFactory.getLogger(CalificacionService.class);

    private final CalificacionRepository calificacionRepository;

    public CalificacionService(CalificacionRepository calificacionRepository) {
        this.calificacionRepository = calificacionRepository;
    }

    /**
     * Save a calificacion.
     *
     * @param calificacion the entity to save.
     * @return the persisted entity.
     */
    public Mono<Calificacion> save(Calificacion calificacion) {
        log.debug("Request to save Calificacion : {}", calificacion);
        return calificacionRepository.save(calificacion);
    }

    /**
     * Update a calificacion.
     *
     * @param calificacion the entity to save.
     * @return the persisted entity.
     */
    public Mono<Calificacion> update(Calificacion calificacion) {
        log.debug("Request to update Calificacion : {}", calificacion);
        return calificacionRepository.save(calificacion);
    }

    /**
     * Partially update a calificacion.
     *
     * @param calificacion the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<Calificacion> partialUpdate(Calificacion calificacion) {
        log.debug("Request to partially update Calificacion : {}", calificacion);

        return calificacionRepository
            .findById(calificacion.getId())
            .map(existingCalificacion -> {
                if (calificacion.getNota() != null) {
                    existingCalificacion.setNota(calificacion.getNota());
                }

                return existingCalificacion;
            })
            .flatMap(calificacionRepository::save);
    }

    /**
     * Get all the calificacions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<Calificacion> findAll() {
        log.debug("Request to get all Calificacions");
        return calificacionRepository.findAll();
    }

    /**
     * Returns the number of calificacions available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return calificacionRepository.count();
    }

    /**
     * Get one calificacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<Calificacion> findOne(Long id) {
        log.debug("Request to get Calificacion : {}", id);
        return calificacionRepository.findById(id);
    }

    /**
     * Delete the calificacion by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Calificacion : {}", id);
        return calificacionRepository.deleteById(id);
    }
}
