package co.edu.usbcali.ingesoft.service;

import co.edu.usbcali.ingesoft.service.dto.DesarrolloDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.usbcali.ingesoft.domain.Desarrollo}.
 */
public interface DesarrolloService {
    /**
     * Save a desarrollo.
     *
     * @param desarrolloDTO the entity to save.
     * @return the persisted entity.
     */
    DesarrolloDTO save(DesarrolloDTO desarrolloDTO);

    /**
     * Updates a desarrollo.
     *
     * @param desarrolloDTO the entity to update.
     * @return the persisted entity.
     */
    DesarrolloDTO update(DesarrolloDTO desarrolloDTO);

    /**
     * Partially updates a desarrollo.
     *
     * @param desarrolloDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DesarrolloDTO> partialUpdate(DesarrolloDTO desarrolloDTO);

    /**
     * Get all the desarrollos.
     *
     * @return the list of entities.
     */
    List<DesarrolloDTO> findAll();

    /**
     * Get the "id" desarrollo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DesarrolloDTO> findOne(Long id);

    /**
     * Delete the "id" desarrollo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
