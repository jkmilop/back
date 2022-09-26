package co.edu.usbcali.ingesoft.web.rest;

import co.edu.usbcali.ingesoft.repository.EstudianteCursoRepository;
import co.edu.usbcali.ingesoft.service.EstudianteCursoService;
import co.edu.usbcali.ingesoft.service.dto.EstudianteCursoDTO;
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
 * REST controller for managing {@link co.edu.usbcali.ingesoft.domain.EstudianteCurso}.
 */
@RestController
@RequestMapping("/api")
public class EstudianteCursoResource {

    private final Logger log = LoggerFactory.getLogger(EstudianteCursoResource.class);

    private static final String ENTITY_NAME = "backEstudianteCurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstudianteCursoService estudianteCursoService;

    private final EstudianteCursoRepository estudianteCursoRepository;

    public EstudianteCursoResource(EstudianteCursoService estudianteCursoService, EstudianteCursoRepository estudianteCursoRepository) {
        this.estudianteCursoService = estudianteCursoService;
        this.estudianteCursoRepository = estudianteCursoRepository;
    }

    /**
     * {@code POST  /estudiante-cursos} : Create a new estudianteCurso.
     *
     * @param estudianteCursoDTO the estudianteCursoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estudianteCursoDTO, or with status {@code 400 (Bad Request)} if the estudianteCurso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estudiante-cursos")
    public ResponseEntity<EstudianteCursoDTO> createEstudianteCurso(@RequestBody EstudianteCursoDTO estudianteCursoDTO)
        throws URISyntaxException {
        log.debug("REST request to save EstudianteCurso : {}", estudianteCursoDTO);
        if (estudianteCursoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estudianteCurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstudianteCursoDTO result = estudianteCursoService.save(estudianteCursoDTO);
        return ResponseEntity
            .created(new URI("/api/estudiante-cursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estudiante-cursos/:id} : Updates an existing estudianteCurso.
     *
     * @param id the id of the estudianteCursoDTO to save.
     * @param estudianteCursoDTO the estudianteCursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estudianteCursoDTO,
     * or with status {@code 400 (Bad Request)} if the estudianteCursoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estudianteCursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estudiante-cursos/{id}")
    public ResponseEntity<EstudianteCursoDTO> updateEstudianteCurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstudianteCursoDTO estudianteCursoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EstudianteCurso : {}, {}", id, estudianteCursoDTO);
        if (estudianteCursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estudianteCursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estudianteCursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstudianteCursoDTO result = estudianteCursoService.update(estudianteCursoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estudianteCursoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estudiante-cursos/:id} : Partial updates given fields of an existing estudianteCurso, field will ignore if it is null
     *
     * @param id the id of the estudianteCursoDTO to save.
     * @param estudianteCursoDTO the estudianteCursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estudianteCursoDTO,
     * or with status {@code 400 (Bad Request)} if the estudianteCursoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the estudianteCursoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the estudianteCursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estudiante-cursos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstudianteCursoDTO> partialUpdateEstudianteCurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstudianteCursoDTO estudianteCursoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstudianteCurso partially : {}, {}", id, estudianteCursoDTO);
        if (estudianteCursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estudianteCursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estudianteCursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstudianteCursoDTO> result = estudianteCursoService.partialUpdate(estudianteCursoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estudianteCursoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /estudiante-cursos} : get all the estudianteCursos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estudianteCursos in body.
     */
    @GetMapping("/estudiante-cursos")
    public List<EstudianteCursoDTO> getAllEstudianteCursos() {
        log.debug("REST request to get all EstudianteCursos");
        return estudianteCursoService.findAll();
    }

    /**
     * {@code GET  /estudiante-cursos/:id} : get the "id" estudianteCurso.
     *
     * @param id the id of the estudianteCursoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estudianteCursoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estudiante-cursos/{id}")
    public ResponseEntity<EstudianteCursoDTO> getEstudianteCurso(@PathVariable Long id) {
        log.debug("REST request to get EstudianteCurso : {}", id);
        Optional<EstudianteCursoDTO> estudianteCursoDTO = estudianteCursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estudianteCursoDTO);
    }

    /**
     * {@code DELETE  /estudiante-cursos/:id} : delete the "id" estudianteCurso.
     *
     * @param id the id of the estudianteCursoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estudiante-cursos/{id}")
    public ResponseEntity<Void> deleteEstudianteCurso(@PathVariable Long id) {
        log.debug("REST request to delete EstudianteCurso : {}", id);
        estudianteCursoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
