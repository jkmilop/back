package co.edu.usbcali.ingesoft.service;

import co.edu.usbcali.ingesoft.service.dto.EstudianteDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.usbcali.ingesoft.domain.Estudiante}.
 */
public interface EstudianteService {
    /**
     * Save a estudiante.
     *
     * @param estudianteDTO the entity to save.
     * @return the persisted entity.
     */
    EstudianteDTO save(EstudianteDTO estudianteDTO);

    /**
     * Updates a estudiante.
     *
     * @param estudianteDTO the entity to update.
     * @return the persisted entity.
     */
    EstudianteDTO update(EstudianteDTO estudianteDTO);

    /**
     * Partially updates a estudiante.
     *
     * @param estudianteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstudianteDTO> partialUpdate(EstudianteDTO estudianteDTO);

    /**
     * Get all the estudiantes.
     *
     * @return the list of entities.
     */
    List<EstudianteDTO> findAll();

    /**
     * Get the "id" estudiante.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstudianteDTO> findOne(Long id);

    /**
     * Delete the "id" estudiante.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
