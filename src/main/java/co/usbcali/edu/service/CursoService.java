package co.usbcali.edu.service;

import co.usbcali.edu.domain.Curso;
import co.usbcali.edu.repository.CursoRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Curso}.
 */
@Service
@Transactional
public class CursoService {

    private final Logger log = LoggerFactory.getLogger(CursoService.class);

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    /**
     * Save a curso.
     *
     * @param curso the entity to save.
     * @return the persisted entity.
     */
    public Mono<Curso> save(Curso curso) {
        log.debug("Request to save Curso : {}", curso);
        return cursoRepository.save(curso);
    }

    /**
     * Update a curso.
     *
     * @param curso the entity to save.
     * @return the persisted entity.
     */
    public Mono<Curso> update(Curso curso) {
        log.debug("Request to update Curso : {}", curso);
        return cursoRepository.save(curso);
    }

    /**
     * Partially update a curso.
     *
     * @param curso the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<Curso> partialUpdate(Curso curso) {
        log.debug("Request to partially update Curso : {}", curso);

        return cursoRepository
            .findById(curso.getId())
            .map(existingCurso -> {
                if (curso.getNombre() != null) {
                    existingCurso.setNombre(curso.getNombre());
                }
                if (curso.getDescripcion() != null) {
                    existingCurso.setDescripcion(curso.getDescripcion());
                }
                if (curso.getEstado() != null) {
                    existingCurso.setEstado(curso.getEstado());
                }
                if (curso.getTipoCurso() != null) {
                    existingCurso.setTipoCurso(curso.getTipoCurso());
                }

                return existingCurso;
            })
            .flatMap(cursoRepository::save);
    }

    /**
     * Get all the cursos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<Curso> findAll() {
        log.debug("Request to get all Cursos");
        return cursoRepository.findAll();
    }

    /**
     * Returns the number of cursos available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return cursoRepository.count();
    }

    /**
     * Get one curso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<Curso> findOne(Long id) {
        log.debug("Request to get Curso : {}", id);
        return cursoRepository.findById(id);
    }

    /**
     * Delete the curso by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Curso : {}", id);
        return cursoRepository.deleteById(id);
    }
}
