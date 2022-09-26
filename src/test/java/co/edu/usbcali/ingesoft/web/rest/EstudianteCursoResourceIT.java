package co.edu.usbcali.ingesoft.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.usbcali.ingesoft.IntegrationTest;
import co.edu.usbcali.ingesoft.domain.EstudianteCurso;
import co.edu.usbcali.ingesoft.repository.EstudianteCursoRepository;
import co.edu.usbcali.ingesoft.service.dto.EstudianteCursoDTO;
import co.edu.usbcali.ingesoft.service.mapper.EstudianteCursoMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EstudianteCursoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstudianteCursoResourceIT {

    private static final String ENTITY_API_URL = "/api/estudiante-cursos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstudianteCursoRepository estudianteCursoRepository;

    @Autowired
    private EstudianteCursoMapper estudianteCursoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstudianteCursoMockMvc;

    private EstudianteCurso estudianteCurso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstudianteCurso createEntity(EntityManager em) {
        EstudianteCurso estudianteCurso = new EstudianteCurso();
        return estudianteCurso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstudianteCurso createUpdatedEntity(EntityManager em) {
        EstudianteCurso estudianteCurso = new EstudianteCurso();
        return estudianteCurso;
    }

    @BeforeEach
    public void initTest() {
        estudianteCurso = createEntity(em);
    }

    @Test
    @Transactional
    void createEstudianteCurso() throws Exception {
        int databaseSizeBeforeCreate = estudianteCursoRepository.findAll().size();
        // Create the EstudianteCurso
        EstudianteCursoDTO estudianteCursoDTO = estudianteCursoMapper.toDto(estudianteCurso);
        restEstudianteCursoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudianteCursoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeCreate + 1);
        EstudianteCurso testEstudianteCurso = estudianteCursoList.get(estudianteCursoList.size() - 1);
    }

    @Test
    @Transactional
    void createEstudianteCursoWithExistingId() throws Exception {
        // Create the EstudianteCurso with an existing ID
        estudianteCurso.setId(1L);
        EstudianteCursoDTO estudianteCursoDTO = estudianteCursoMapper.toDto(estudianteCurso);

        int databaseSizeBeforeCreate = estudianteCursoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstudianteCursoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudianteCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstudianteCursos() throws Exception {
        // Initialize the database
        estudianteCursoRepository.saveAndFlush(estudianteCurso);

        // Get all the estudianteCursoList
        restEstudianteCursoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estudianteCurso.getId().intValue())));
    }

    @Test
    @Transactional
    void getEstudianteCurso() throws Exception {
        // Initialize the database
        estudianteCursoRepository.saveAndFlush(estudianteCurso);

        // Get the estudianteCurso
        restEstudianteCursoMockMvc
            .perform(get(ENTITY_API_URL_ID, estudianteCurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estudianteCurso.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEstudianteCurso() throws Exception {
        // Get the estudianteCurso
        restEstudianteCursoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstudianteCurso() throws Exception {
        // Initialize the database
        estudianteCursoRepository.saveAndFlush(estudianteCurso);

        int databaseSizeBeforeUpdate = estudianteCursoRepository.findAll().size();

        // Update the estudianteCurso
        EstudianteCurso updatedEstudianteCurso = estudianteCursoRepository.findById(estudianteCurso.getId()).get();
        // Disconnect from session so that the updates on updatedEstudianteCurso are not directly saved in db
        em.detach(updatedEstudianteCurso);
        EstudianteCursoDTO estudianteCursoDTO = estudianteCursoMapper.toDto(updatedEstudianteCurso);

        restEstudianteCursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estudianteCursoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estudianteCursoDTO))
            )
            .andExpect(status().isOk());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeUpdate);
        EstudianteCurso testEstudianteCurso = estudianteCursoList.get(estudianteCursoList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingEstudianteCurso() throws Exception {
        int databaseSizeBeforeUpdate = estudianteCursoRepository.findAll().size();
        estudianteCurso.setId(count.incrementAndGet());

        // Create the EstudianteCurso
        EstudianteCursoDTO estudianteCursoDTO = estudianteCursoMapper.toDto(estudianteCurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstudianteCursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estudianteCursoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estudianteCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstudianteCurso() throws Exception {
        int databaseSizeBeforeUpdate = estudianteCursoRepository.findAll().size();
        estudianteCurso.setId(count.incrementAndGet());

        // Create the EstudianteCurso
        EstudianteCursoDTO estudianteCursoDTO = estudianteCursoMapper.toDto(estudianteCurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudianteCursoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estudianteCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstudianteCurso() throws Exception {
        int databaseSizeBeforeUpdate = estudianteCursoRepository.findAll().size();
        estudianteCurso.setId(count.incrementAndGet());

        // Create the EstudianteCurso
        EstudianteCursoDTO estudianteCursoDTO = estudianteCursoMapper.toDto(estudianteCurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudianteCursoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudianteCursoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstudianteCursoWithPatch() throws Exception {
        // Initialize the database
        estudianteCursoRepository.saveAndFlush(estudianteCurso);

        int databaseSizeBeforeUpdate = estudianteCursoRepository.findAll().size();

        // Update the estudianteCurso using partial update
        EstudianteCurso partialUpdatedEstudianteCurso = new EstudianteCurso();
        partialUpdatedEstudianteCurso.setId(estudianteCurso.getId());

        restEstudianteCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstudianteCurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstudianteCurso))
            )
            .andExpect(status().isOk());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeUpdate);
        EstudianteCurso testEstudianteCurso = estudianteCursoList.get(estudianteCursoList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateEstudianteCursoWithPatch() throws Exception {
        // Initialize the database
        estudianteCursoRepository.saveAndFlush(estudianteCurso);

        int databaseSizeBeforeUpdate = estudianteCursoRepository.findAll().size();

        // Update the estudianteCurso using partial update
        EstudianteCurso partialUpdatedEstudianteCurso = new EstudianteCurso();
        partialUpdatedEstudianteCurso.setId(estudianteCurso.getId());

        restEstudianteCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstudianteCurso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstudianteCurso))
            )
            .andExpect(status().isOk());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeUpdate);
        EstudianteCurso testEstudianteCurso = estudianteCursoList.get(estudianteCursoList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingEstudianteCurso() throws Exception {
        int databaseSizeBeforeUpdate = estudianteCursoRepository.findAll().size();
        estudianteCurso.setId(count.incrementAndGet());

        // Create the EstudianteCurso
        EstudianteCursoDTO estudianteCursoDTO = estudianteCursoMapper.toDto(estudianteCurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstudianteCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estudianteCursoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estudianteCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstudianteCurso() throws Exception {
        int databaseSizeBeforeUpdate = estudianteCursoRepository.findAll().size();
        estudianteCurso.setId(count.incrementAndGet());

        // Create the EstudianteCurso
        EstudianteCursoDTO estudianteCursoDTO = estudianteCursoMapper.toDto(estudianteCurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudianteCursoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estudianteCursoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstudianteCurso() throws Exception {
        int databaseSizeBeforeUpdate = estudianteCursoRepository.findAll().size();
        estudianteCurso.setId(count.incrementAndGet());

        // Create the EstudianteCurso
        EstudianteCursoDTO estudianteCursoDTO = estudianteCursoMapper.toDto(estudianteCurso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudianteCursoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estudianteCursoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstudianteCurso in the database
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstudianteCurso() throws Exception {
        // Initialize the database
        estudianteCursoRepository.saveAndFlush(estudianteCurso);

        int databaseSizeBeforeDelete = estudianteCursoRepository.findAll().size();

        // Delete the estudianteCurso
        restEstudianteCursoMockMvc
            .perform(delete(ENTITY_API_URL_ID, estudianteCurso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstudianteCurso> estudianteCursoList = estudianteCursoRepository.findAll();
        assertThat(estudianteCursoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
