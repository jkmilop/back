package co.edu.usbcali.ingesoft.service;

import co.edu.usbcali.ingesoft.service.dto.EstudianteCursoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.usbcali.ingesoft.domain.EstudianteCurso}.
 */
public interface EstudianteCursoService {
    /**
     * Save a estudianteCurso.
     *
     * @param estudianteCursoDTO the entity to save.
     * @return the persisted entity.
     */
    EstudianteCursoDTO save(EstudianteCursoDTO estudianteCursoDTO);

    /**
     * Updates a estudianteCurso.
     *
     * @param estudianteCursoDTO the entity to update.
     * @return the persisted entity.
     */
    EstudianteCursoDTO update(EstudianteCursoDTO estudianteCursoDTO);

    /**
     * Partially updates a estudianteCurso.
     *
     * @param estudianteCursoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstudianteCursoDTO> partialUpdate(EstudianteCursoDTO estudianteCursoDTO);

    /**
     * Get all the estudianteCursos.
     *
     * @return the list of entities.
     */
    List<EstudianteCursoDTO> findAll();

    /**
     * Get the "id" estudianteCurso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstudianteCursoDTO> findOne(Long id);

    /**
     * Delete the "id" estudianteCurso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
