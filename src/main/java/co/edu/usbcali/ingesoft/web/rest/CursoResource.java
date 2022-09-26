package co.edu.usbcali.ingesoft.web.rest;

import co.edu.usbcali.ingesoft.repository.CursoRepository;
import co.edu.usbcali.ingesoft.service.CursoService;
import co.edu.usbcali.ingesoft.service.dto.CursoDTO;
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
 * REST controller for managing {@link co.edu.usbcali.ingesoft.domain.Curso}.
 */
@RestController
@RequestMapping("/api")
public class CursoResource {

    private final Logger log = LoggerFactory.getLogger(CursoResource.class);

    private static final String ENTITY_NAME = "backCurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CursoService cursoService;

    private final CursoRepository cursoRepository;

    public CursoResource(CursoService cursoService, CursoRepository cursoRepository) {
        this.cursoService = cursoService;
        this.cursoRepository = cursoRepository;
    }

    /**
     * {@code POST  /cursos} : Create a new curso.
     *
     * @param cursoDTO the cursoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cursoDTO, or with status {@code 400 (Bad Request)} if the curso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cursos")
    public ResponseEntity<CursoDTO> createCurso(@RequestBody CursoDTO cursoDTO) throws URISyntaxException {
        log.debug("REST request to save Curso : {}", cursoDTO);
        if (cursoDTO.getId() != null) {
            throw new BadRequestAlertException("A new curso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CursoDTO result = cursoService.save(cursoDTO);
        return ResponseEntity
            .created(new URI("/api/cursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cursos/:id} : Updates an existing curso.
     *
     * @param id the id of the cursoDTO to save.
     * @param cursoDTO the cursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cursoDTO,
     * or with status {@code 400 (Bad Request)} if the cursoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cursos/{id}")
    public ResponseEntity<CursoDTO> updateCurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CursoDTO cursoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Curso : {}, {}", id, cursoDTO);
        if (cursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CursoDTO result = cursoService.update(cursoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cursoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cursos/:id} : Partial updates given fields of an existing curso, field will ignore if it is null
     *
     * @param id the id of the cursoDTO to save.
     * @param cursoDTO the cursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cursoDTO,
     * or with status {@code 400 (Bad Request)} if the cursoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cursoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cursos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CursoDTO> partialUpdateCurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CursoDTO cursoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Curso partially : {}, {}", id, cursoDTO);
        if (cursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CursoDTO> result = cursoService.partialUpdate(cursoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cursoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cursos} : get all the cursos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cursos in body.
     */
    @GetMapping("/cursos")
    public List<CursoDTO> getAllCursos() {
        log.debug("REST request to get all Cursos");
        return cursoService.findAll();
    }

    /**
     * {@code GET  /cursos/:id} : get the "id" curso.
     *
     * @param id the id of the cursoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cursoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cursos/{id}")
    public ResponseEntity<CursoDTO> getCurso(@PathVariable Long id) {
        log.debug("REST request to get Curso : {}", id);
        Optional<CursoDTO> cursoDTO = cursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cursoDTO);
    }

    /**
     * {@code DELETE  /cursos/:id} : delete the "id" curso.
     *
     * @param id the id of the cursoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        log.debug("REST request to delete Curso : {}", id);
        cursoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
