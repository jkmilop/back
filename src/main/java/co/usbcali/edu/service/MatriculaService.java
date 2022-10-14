package co.usbcali.edu.service;

import co.usbcali.edu.domain.Matricula;
import co.usbcali.edu.repository.MatriculaRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Matricula}.
 */
@Service
@Transactional
public class MatriculaService {

    private final Logger log = LoggerFactory.getLogger(MatriculaService.class);

    private final MatriculaRepository matriculaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    /**
     * Save a matricula.
     *
     * @param matricula the entity to save.
     * @return the persisted entity.
     */
    public Mono<Matricula> save(Matricula matricula) {
        log.debug("Request to save Matricula : {}", matricula);
        return matriculaRepository.save(matricula);
    }

    /**
     * Update a matricula.
     *
     * @param matricula the entity to save.
     * @return the persisted entity.
     */
    public Mono<Matricula> update(Matricula matricula) {
        log.debug("Request to update Matricula : {}", matricula);
        return matriculaRepository.save(matricula);
    }

    /**
     * Partially update a matricula.
     *
     * @param matricula the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<Matricula> partialUpdate(Matricula matricula) {
        log.debug("Request to partially update Matricula : {}", matricula);

        return matriculaRepository
            .findById(matricula.getId())
            .map(existingMatricula -> {
                return existingMatricula;
            })
            .flatMap(matriculaRepository::save);
    }

    /**
     * Get all the matriculas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<Matricula> findAll() {
        log.debug("Request to get all Matriculas");
        return matriculaRepository.findAll();
    }

    /**
     * Returns the number of matriculas available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return matriculaRepository.count();
    }

    /**
     * Get one matricula by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<Matricula> findOne(Long id) {
        log.debug("Request to get Matricula : {}", id);
        return matriculaRepository.findById(id);
    }

    /**
     * Delete the matricula by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Matricula : {}", id);
        return matriculaRepository.deleteById(id);
    }
}
