package co.edu.usbcali.ingesoft.web.rest;

import co.edu.usbcali.ingesoft.repository.ProfesorRepository;
import co.edu.usbcali.ingesoft.service.ProfesorService;
import co.edu.usbcali.ingesoft.service.dto.ProfesorDTO;
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
 * REST controller for managing {@link co.edu.usbcali.ingesoft.domain.Profesor}.
 */
@RestController
@RequestMapping("/api")
public class ProfesorResource {

    private final Logger log = LoggerFactory.getLogger(ProfesorResource.class);

    private static final String ENTITY_NAME = "backProfesor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfesorService profesorService;

    private final ProfesorRepository profesorRepository;

    public ProfesorResource(ProfesorService profesorService, ProfesorRepository profesorRepository) {
        this.profesorService = profesorService;
        this.profesorRepository = profesorRepository;
    }

    /**
     * {@code POST  /profesors} : Create a new profesor.
     *
     * @param profesorDTO the profesorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profesorDTO, or with status {@code 400 (Bad Request)} if the profesor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profesors")
    public ResponseEntity<ProfesorDTO> createProfesor(@RequestBody ProfesorDTO profesorDTO) throws URISyntaxException {
        log.debug("REST request to save Profesor : {}", profesorDTO);
        if (profesorDTO.getId() != null) {
            throw new BadRequestAlertException("A new profesor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfesorDTO result = profesorService.save(profesorDTO);
        return ResponseEntity
            .created(new URI("/api/profesors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profesors/:id} : Updates an existing profesor.
     *
     * @param id the id of the profesorDTO to save.
     * @param profesorDTO the profesorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profesorDTO,
     * or with status {@code 400 (Bad Request)} if the profesorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profesorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profesors/{id}")
    public ResponseEntity<ProfesorDTO> updateProfesor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProfesorDTO profesorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Profesor : {}, {}", id, profesorDTO);
        if (profesorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profesorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profesorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProfesorDTO result = profesorService.update(profesorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profesorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /profesors/:id} : Partial updates given fields of an existing profesor, field will ignore if it is null
     *
     * @param id the id of the profesorDTO to save.
     * @param profesorDTO the profesorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profesorDTO,
     * or with status {@code 400 (Bad Request)} if the profesorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the profesorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the profesorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/profesors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProfesorDTO> partialUpdateProfesor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProfesorDTO profesorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Profesor partially : {}, {}", id, profesorDTO);
        if (profesorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profesorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profesorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProfesorDTO> result = profesorService.partialUpdate(profesorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profesorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /profesors} : get all the profesors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profesors in body.
     */
    @GetMapping("/profesors")
    public List<ProfesorDTO> getAllProfesors() {
        log.debug("REST request to get all Profesors");
        return profesorService.findAll();
    }

    /**
     * {@code GET  /profesors/:id} : get the "id" profesor.
     *
     * @param id the id of the profesorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profesorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profesors/{id}")
    public ResponseEntity<ProfesorDTO> getProfesor(@PathVariable Long id) {
        log.debug("REST request to get Profesor : {}", id);
        Optional<ProfesorDTO> profesorDTO = profesorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profesorDTO);
    }

    /**
     * {@code DELETE  /profesors/:id} : delete the "id" profesor.
     *
     * @param id the id of the profesorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profesors/{id}")
    public ResponseEntity<Void> deleteProfesor(@PathVariable Long id) {
        log.debug("REST request to delete Profesor : {}", id);
        profesorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
