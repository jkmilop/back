package co.edu.usbcali.ingesoft.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.usbcali.ingesoft.IntegrationTest;
import co.edu.usbcali.ingesoft.domain.Profesor;
import co.edu.usbcali.ingesoft.repository.ProfesorRepository;
import co.edu.usbcali.ingesoft.service.dto.ProfesorDTO;
import co.edu.usbcali.ingesoft.service.mapper.ProfesorMapper;
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
 * Integration tests for the {@link ProfesorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProfesorResourceIT {

    private static final String DEFAULT_PROFESOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROFESOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_PROFESOR = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_PROFESOR = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/profesors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private ProfesorMapper profesorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfesorMockMvc;

    private Profesor profesor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profesor createEntity(EntityManager em) {
        Profesor profesor = new Profesor()
            .profesorName(DEFAULT_PROFESOR_NAME)
            .codigoProfesor(DEFAULT_CODIGO_PROFESOR)
            .correo(DEFAULT_CORREO);
        return profesor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profesor createUpdatedEntity(EntityManager em) {
        Profesor profesor = new Profesor()
            .profesorName(UPDATED_PROFESOR_NAME)
            .codigoProfesor(UPDATED_CODIGO_PROFESOR)
            .correo(UPDATED_CORREO);
        return profesor;
    }

    @BeforeEach
    public void initTest() {
        profesor = createEntity(em);
    }

    @Test
    @Transactional
    void createProfesor() throws Exception {
        int databaseSizeBeforeCreate = profesorRepository.findAll().size();
        // Create the Profesor
        ProfesorDTO profesorDTO = profesorMapper.toDto(profesor);
        restProfesorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profesorDTO)))
            .andExpect(status().isCreated());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeCreate + 1);
        Profesor testProfesor = profesorList.get(profesorList.size() - 1);
        assertThat(testProfesor.getProfesorName()).isEqualTo(DEFAULT_PROFESOR_NAME);
        assertThat(testProfesor.getCodigoProfesor()).isEqualTo(DEFAULT_CODIGO_PROFESOR);
        assertThat(testProfesor.getCorreo()).isEqualTo(DEFAULT_CORREO);
    }

    @Test
    @Transactional
    void createProfesorWithExistingId() throws Exception {
        // Create the Profesor with an existing ID
        profesor.setId(1L);
        ProfesorDTO profesorDTO = profesorMapper.toDto(profesor);

        int databaseSizeBeforeCreate = profesorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfesorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profesorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProfesors() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        // Get all the profesorList
        restProfesorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profesor.getId().intValue())))
            .andExpect(jsonPath("$.[*].profesorName").value(hasItem(DEFAULT_PROFESOR_NAME)))
            .andExpect(jsonPath("$.[*].codigoProfesor").value(hasItem(DEFAULT_CODIGO_PROFESOR)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)));
    }

    @Test
    @Transactional
    void getProfesor() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        // Get the profesor
        restProfesorMockMvc
            .perform(get(ENTITY_API_URL_ID, profesor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profesor.getId().intValue()))
            .andExpect(jsonPath("$.profesorName").value(DEFAULT_PROFESOR_NAME))
            .andExpect(jsonPath("$.codigoProfesor").value(DEFAULT_CODIGO_PROFESOR))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO));
    }

    @Test
    @Transactional
    void getNonExistingProfesor() throws Exception {
        // Get the profesor
        restProfesorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfesor() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();

        // Update the profesor
        Profesor updatedProfesor = profesorRepository.findById(profesor.getId()).get();
        // Disconnect from session so that the updates on updatedProfesor are not directly saved in db
        em.detach(updatedProfesor);
        updatedProfesor.profesorName(UPDATED_PROFESOR_NAME).codigoProfesor(UPDATED_CODIGO_PROFESOR).correo(UPDATED_CORREO);
        ProfesorDTO profesorDTO = profesorMapper.toDto(updatedProfesor);

        restProfesorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profesorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profesorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
        Profesor testProfesor = profesorList.get(profesorList.size() - 1);
        assertThat(testProfesor.getProfesorName()).isEqualTo(UPDATED_PROFESOR_NAME);
        assertThat(testProfesor.getCodigoProfesor()).isEqualTo(UPDATED_CODIGO_PROFESOR);
        assertThat(testProfesor.getCorreo()).isEqualTo(UPDATED_CORREO);
    }

    @Test
    @Transactional
    void putNonExistingProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();
        profesor.setId(count.incrementAndGet());

        // Create the Profesor
        ProfesorDTO profesorDTO = profesorMapper.toDto(profesor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfesorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profesorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profesorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();
        profesor.setId(count.incrementAndGet());

        // Create the Profesor
        ProfesorDTO profesorDTO = profesorMapper.toDto(profesor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfesorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profesorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();
        profesor.setId(count.incrementAndGet());

        // Create the Profesor
        ProfesorDTO profesorDTO = profesorMapper.toDto(profesor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfesorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profesorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfesorWithPatch() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();

        // Update the profesor using partial update
        Profesor partialUpdatedProfesor = new Profesor();
        partialUpdatedProfesor.setId(profesor.getId());

        partialUpdatedProfesor.profesorName(UPDATED_PROFESOR_NAME).codigoProfesor(UPDATED_CODIGO_PROFESOR).correo(UPDATED_CORREO);

        restProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfesor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfesor))
            )
            .andExpect(status().isOk());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
        Profesor testProfesor = profesorList.get(profesorList.size() - 1);
        assertThat(testProfesor.getProfesorName()).isEqualTo(UPDATED_PROFESOR_NAME);
        assertThat(testProfesor.getCodigoProfesor()).isEqualTo(UPDATED_CODIGO_PROFESOR);
        assertThat(testProfesor.getCorreo()).isEqualTo(UPDATED_CORREO);
    }

    @Test
    @Transactional
    void fullUpdateProfesorWithPatch() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();

        // Update the profesor using partial update
        Profesor partialUpdatedProfesor = new Profesor();
        partialUpdatedProfesor.setId(profesor.getId());

        partialUpdatedProfesor.profesorName(UPDATED_PROFESOR_NAME).codigoProfesor(UPDATED_CODIGO_PROFESOR).correo(UPDATED_CORREO);

        restProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfesor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfesor))
            )
            .andExpect(status().isOk());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
        Profesor testProfesor = profesorList.get(profesorList.size() - 1);
        assertThat(testProfesor.getProfesorName()).isEqualTo(UPDATED_PROFESOR_NAME);
        assertThat(testProfesor.getCodigoProfesor()).isEqualTo(UPDATED_CODIGO_PROFESOR);
        assertThat(testProfesor.getCorreo()).isEqualTo(UPDATED_CORREO);
    }

    @Test
    @Transactional
    void patchNonExistingProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();
        profesor.setId(count.incrementAndGet());

        // Create the Profesor
        ProfesorDTO profesorDTO = profesorMapper.toDto(profesor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profesorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profesorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();
        profesor.setId(count.incrementAndGet());

        // Create the Profesor
        ProfesorDTO profesorDTO = profesorMapper.toDto(profesor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profesorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();
        profesor.setId(count.incrementAndGet());

        // Create the Profesor
        ProfesorDTO profesorDTO = profesorMapper.toDto(profesor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfesorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(profesorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfesor() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(profesor);

        int databaseSizeBeforeDelete = profesorRepository.findAll().size();

        // Delete the profesor
        restProfesorMockMvc
            .perform(delete(ENTITY_API_URL_ID, profesor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
