package co.edu.usbcali.ingesoft.web.rest;

import co.edu.usbcali.ingesoft.repository.EstudianteRepository;
import co.edu.usbcali.ingesoft.service.EstudianteService;
import co.edu.usbcali.ingesoft.service.dto.EstudianteDTO;
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
 * REST controller for managing {@link co.edu.usbcali.ingesoft.domain.Estudiante}.
 */
@RestController
@RequestMapping("/api")
public class EstudianteResource {

    private final Logger log = LoggerFactory.getLogger(EstudianteResource.class);

    private static final String ENTITY_NAME = "backEstudiante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstudianteService estudianteService;

    private final EstudianteRepository estudianteRepository;

    public EstudianteResource(EstudianteService estudianteService, EstudianteRepository estudianteRepository) {
        this.estudianteService = estudianteService;
        this.estudianteRepository = estudianteRepository;
    }

    /**
     * {@code POST  /estudiantes} : Create a new estudiante.
     *
     * @param estudianteDTO the estudianteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estudianteDTO, or with status {@code 400 (Bad Request)} if the estudiante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estudiantes")
    public ResponseEntity<EstudianteDTO> createEstudiante(@RequestBody EstudianteDTO estudianteDTO) throws URISyntaxException {
        log.debug("REST request to save Estudiante : {}", estudianteDTO);
        if (estudianteDTO.getId() != null) {
            throw new BadRequestAlertException("A new estudiante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstudianteDTO result = estudianteService.save(estudianteDTO);
        return ResponseEntity
            .created(new URI("/api/estudiantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estudiantes/:id} : Updates an existing estudiante.
     *
     * @param id the id of the estudianteDTO to save.
     * @param estudianteDTO the estudianteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estudianteDTO,
     * or with status {@code 400 (Bad Request)} if the estudianteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estudianteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estudiantes/{id}")
    public ResponseEntity<EstudianteDTO> updateEstudiante(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstudianteDTO estudianteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Estudiante : {}, {}", id, estudianteDTO);
        if (estudianteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estudianteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estudianteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstudianteDTO result = estudianteService.update(estudianteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estudianteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estudiantes/:id} : Partial updates given fields of an existing estudiante, field will ignore if it is null
     *
     * @param id the id of the estudianteDTO to save.
     * @param estudianteDTO the estudianteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estudianteDTO,
     * or with status {@code 400 (Bad Request)} if the estudianteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the estudianteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the estudianteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estudiantes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstudianteDTO> partialUpdateEstudiante(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstudianteDTO estudianteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Estudiante partially : {}, {}", id, estudianteDTO);
        if (estudianteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estudianteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estudianteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstudianteDTO> result = estudianteService.partialUpdate(estudianteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estudianteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /estudiantes} : get all the estudiantes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estudiantes in body.
     */
    @GetMapping("/estudiantes")
    public List<EstudianteDTO> getAllEstudiantes() {
        log.debug("REST request to get all Estudiantes");
        return estudianteService.findAll();
    }

    /**
     * {@code GET  /estudiantes/:id} : get the "id" estudiante.
     *
     * @param id the id of the estudianteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estudianteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estudiantes/{id}")
    public ResponseEntity<EstudianteDTO> getEstudiante(@PathVariable Long id) {
        log.debug("REST request to get Estudiante : {}", id);
        Optional<EstudianteDTO> estudianteDTO = estudianteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estudianteDTO);
    }

    /**
     * {@code DELETE  /estudiantes/:id} : delete the "id" estudiante.
     *
     * @param id the id of the estudianteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estudiantes/{id}")
    public ResponseEntity<Void> deleteEstudiante(@PathVariable Long id) {
        log.debug("REST request to delete Estudiante : {}", id);
        estudianteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
