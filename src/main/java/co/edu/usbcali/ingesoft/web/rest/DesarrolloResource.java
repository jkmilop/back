package co.edu.usbcali.ingesoft.web.rest;

import co.edu.usbcali.ingesoft.repository.DesarrolloRepository;
import co.edu.usbcali.ingesoft.service.DesarrolloService;
import co.edu.usbcali.ingesoft.service.dto.DesarrolloDTO;
import co.edu.usbcali.ingesoft.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.usbcali.ingesoft.domain.Desarrollo}.
 */
@RestController
@RequestMapping("/api")
public class DesarrolloResource {

    private final Logger log = LoggerFactory.getLogger(DesarrolloResource.class);

    private static final String ENTITY_NAME = "backDesarrollo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DesarrolloService desarrolloService;

    private final DesarrolloRepository desarrolloRepository;

    public DesarrolloResource(DesarrolloService desarrolloService, DesarrolloRepository desarrolloRepository) {
        this.desarrolloService = desarrolloService;
        this.desarrolloRepository = desarrolloRepository;
    }

    /**
     * {@code POST  /desarrollos} : Create a new desarrollo.
     *
     * @param desarrolloDTO the desarrolloDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new desarrolloDTO, or with status {@code 400 (Bad Request)} if the desarrollo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/desarrollos")
    public ResponseEntity<DesarrolloDTO> createDesarrollo(@RequestBody DesarrolloDTO desarrolloDTO) throws URISyntaxException {
        log.debug("REST request to save Desarrollo : {}", desarrolloDTO);
        if (desarrolloDTO.getId() != null) {
            throw new BadRequestAlertException("A new desarrollo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DesarrolloDTO result = desarrolloService.save(desarrolloDTO);
        return ResponseEntity
            .created(new URI("/api/desarrollos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /desarrollos/:id} : Updates an existing desarrollo.
     *
     * @param id the id of the desarrolloDTO to save.
     * @param desarrolloDTO the desarrolloDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated desarrolloDTO,
     * or with status {@code 400 (Bad Request)} if the desarrolloDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the desarrolloDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/desarrollos/{id}")
    public ResponseEntity<DesarrolloDTO> updateDesarrollo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DesarrolloDTO desarrolloDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Desarrollo : {}, {}", id, desarrolloDTO);
        if (desarrolloDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, desarrolloDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!desarrolloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DesarrolloDTO result = desarrolloService.update(desarrolloDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, desarrolloDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /desarrollos/:id} : Partial updates given fields of an existing desarrollo, field will ignore if it is null
     *
     * @param id the id of the desarrolloDTO to save.
     * @param desarrolloDTO the desarrolloDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated desarrolloDTO,
     * or with status {@code 400 (Bad Request)} if the desarrolloDTO is not valid,
     * or with status {@code 404 (Not Found)} if the desarrolloDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the desarrolloDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/desarrollos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DesarrolloDTO> partialUpdateDesarrollo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DesarrolloDTO desarrolloDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Desarrollo partially : {}, {}", id, desarrolloDTO);
        if (desarrolloDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, desarrolloDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!desarrolloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DesarrolloDTO> result = desarrolloService.partialUpdate(desarrolloDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, desarrolloDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /desarrollos} : get all the desarrollos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of desarrollos in body.
     */
    @GetMapping("/desarrollos")
    public List<DesarrolloDTO> getAllDesarrollos() {
        log.debug("REST request to get all Desarrollos");
        return desarrolloService.findAll();
    }

    /**
     * {@code GET  /desarrollos/:id} : get the "id" desarrollo.
     *
     * @param id the id of the desarrolloDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the desarrolloDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/desarrollos/{id}")
    public ResponseEntity<DesarrolloDTO> getDesarrollo(@PathVariable Long id) {
        log.debug("REST request to get Desarrollo : {}", id);
        Optional<DesarrolloDTO> desarrolloDTO = desarrolloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(desarrolloDTO);
    }

    /**
     * {@code DELETE  /desarrollos/:id} : delete the "id" desarrollo.
     *
     * @param id the id of the desarrolloDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/desarrollos/{id}")
    public ResponseEntity<Void> deleteDesarrollo(@PathVariable Long id) {
        log.debug("REST request to delete Desarrollo : {}", id);
        desarrolloService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
