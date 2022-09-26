package co.edu.usbcali.ingesoft.service;

import co.edu.usbcali.ingesoft.service.dto.ProfesorDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.usbcali.ingesoft.domain.Profesor}.
 */
public interface ProfesorService {
    /**
     * Save a profesor.
     *
     * @param profesorDTO the entity to save.
     * @return the persisted entity.
     */
    ProfesorDTO save(ProfesorDTO profesorDTO);

    /**
     * Updates a profesor.
     *
     * @param profesorDTO the entity to update.
     * @return the persisted entity.
     */
    ProfesorDTO update(ProfesorDTO profesorDTO);

    /**
     * Partially updates a profesor.
     *
     * @param profesorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProfesorDTO> partialUpdate(ProfesorDTO profesorDTO);

    /**
     * Get all the profesors.
     *
     * @return the list of entities.
     */
    List<ProfesorDTO> findAll();

    /**
     * Get the "id" profesor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfesorDTO> findOne(Long id);

    /**
     * Delete the "id" profesor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
