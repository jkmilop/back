package co.usbcali.edu.service;

import co.usbcali.edu.domain.Actividad;
import co.usbcali.edu.repository.ActividadRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Actividad}.
 */
@Service
@Transactional
public class ActividadService {

    private final Logger log = LoggerFactory.getLogger(ActividadService.class);

    private final ActividadRepository actividadRepository;

    public ActividadService(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    /**
     * Save a actividad.
     *
     * @param actividad the entity to save.
     * @return the persisted entity.
     */
    public Mono<Actividad> save(Actividad actividad) {
        log.debug("Request to save Actividad : {}", actividad);
        return actividadRepository.save(actividad);
    }

    /**
     * Update a actividad.
     *
     * @param actividad the entity to save.
     * @return the persisted entity.
     */
    public Mono<Actividad> update(Actividad actividad) {
        log.debug("Request to update Actividad : {}", actividad);
        return actividadRepository.save(actividad);
    }

    /**
     * Partially update a actividad.
     *
     * @param actividad the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<Actividad> partialUpdate(Actividad actividad) {
        log.debug("Request to partially update Actividad : {}", actividad);

        return actividadRepository
            .findById(actividad.getId())
            .map(existingActividad -> {
                if (actividad.getNombre() != null) {
                    existingActividad.setNombre(actividad.getNombre());
                }
                if (actividad.getDescription() != null) {
                    existingActividad.setDescription(actividad.getDescription());
                }
                if (actividad.getEstado() != null) {
                    existingActividad.setEstado(actividad.getEstado());
                }
                if (actividad.getFormato() != null) {
                    existingActividad.setFormato(actividad.getFormato());
                }

                return existingActividad;
            })
            .flatMap(actividadRepository::save);
    }

    /**
     * Get all the actividads.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<Actividad> findAll() {
        log.debug("Request to get all Actividads");
        return actividadRepository.findAll();
    }

    /**
     * Returns the number of actividads available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return actividadRepository.count();
    }

    /**
     * Get one actividad by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<Actividad> findOne(Long id) {
        log.debug("Request to get Actividad : {}", id);
        return actividadRepository.findById(id);
    }

    /**
     * Delete the actividad by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Actividad : {}", id);
        return actividadRepository.deleteById(id);
    }
}
