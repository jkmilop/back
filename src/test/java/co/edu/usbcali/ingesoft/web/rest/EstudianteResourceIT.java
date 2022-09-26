package co.edu.usbcali.ingesoft.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.usbcali.ingesoft.IntegrationTest;
import co.edu.usbcali.ingesoft.domain.Estudiante;
import co.edu.usbcali.ingesoft.repository.EstudianteRepository;
import co.edu.usbcali.ingesoft.service.dto.EstudianteDTO;
import co.edu.usbcali.ingesoft.service.mapper.EstudianteMapper;
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
 * Integration tests for the {@link EstudianteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstudianteResourceIT {

    private static final String DEFAULT_ESTUDIANTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ESTUDIANTE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_ESTUDIANTE = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ESTUDIANTE = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estudiantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EstudianteMapper estudianteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstudianteMockMvc;

    private Estudiante estudiante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estudiante createEntity(EntityManager em) {
        Estudiante estudiante = new Estudiante()
            .estudianteName(DEFAULT_ESTUDIANTE_NAME)
            .codigoEstudiante(DEFAULT_CODIGO_ESTUDIANTE)
            .correo(DEFAULT_CORREO);
        return estudiante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estudiante createUpdatedEntity(EntityManager em) {
        Estudiante estudiante = new Estudiante()
            .estudianteName(UPDATED_ESTUDIANTE_NAME)
            .codigoEstudiante(UPDATED_CODIGO_ESTUDIANTE)
            .correo(UPDATED_CORREO);
        return estudiante;
    }

    @BeforeEach
    public void initTest() {
        estudiante = createEntity(em);
    }

    @Test
    @Transactional
    void createEstudiante() throws Exception {
        int databaseSizeBeforeCreate = estudianteRepository.findAll().size();
        // Create the Estudiante
        EstudianteDTO estudianteDTO = estudianteMapper.toDto(estudiante);
        restEstudianteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudianteDTO)))
            .andExpect(status().isCreated());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeCreate + 1);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getEstudianteName()).isEqualTo(DEFAULT_ESTUDIANTE_NAME);
        assertThat(testEstudiante.getCodigoEstudiante()).isEqualTo(DEFAULT_CODIGO_ESTUDIANTE);
        assertThat(testEstudiante.getCorreo()).isEqualTo(DEFAULT_CORREO);
    }

    @Test
    @Transactional
    void createEstudianteWithExistingId() throws Exception {
        // Create the Estudiante with an existing ID
        estudiante.setId(1L);
        EstudianteDTO estudianteDTO = estudianteMapper.toDto(estudiante);

        int databaseSizeBeforeCreate = estudianteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstudianteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudianteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstudiantes() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        // Get all the estudianteList
        restEstudianteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estudiante.getId().intValue())))
            .andExpect(jsonPath("$.[*].estudianteName").value(hasItem(DEFAULT_ESTUDIANTE_NAME)))
            .andExpect(jsonPath("$.[*].codigoEstudiante").value(hasItem(DEFAULT_CODIGO_ESTUDIANTE)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)));
    }

    @Test
    @Transactional
    void getEstudiante() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        // Get the estudiante
        restEstudianteMockMvc
            .perform(get(ENTITY_API_URL_ID, estudiante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estudiante.getId().intValue()))
            .andExpect(jsonPath("$.estudianteName").value(DEFAULT_ESTUDIANTE_NAME))
            .andExpect(jsonPath("$.codigoEstudiante").value(DEFAULT_CODIGO_ESTUDIANTE))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO));
    }

    @Test
    @Transactional
    void getNonExistingEstudiante() throws Exception {
        // Get the estudiante
        restEstudianteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstudiante() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();

        // Update the estudiante
        Estudiante updatedEstudiante = estudianteRepository.findById(estudiante.getId()).get();
        // Disconnect from session so that the updates on updatedEstudiante are not directly saved in db
        em.detach(updatedEstudiante);
        updatedEstudiante.estudianteName(UPDATED_ESTUDIANTE_NAME).codigoEstudiante(UPDATED_CODIGO_ESTUDIANTE).correo(UPDATED_CORREO);
        EstudianteDTO estudianteDTO = estudianteMapper.toDto(updatedEstudiante);

        restEstudianteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estudianteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estudianteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getEstudianteName()).isEqualTo(UPDATED_ESTUDIANTE_NAME);
        assertThat(testEstudiante.getCodigoEstudiante()).isEqualTo(UPDATED_CODIGO_ESTUDIANTE);
        assertThat(testEstudiante.getCorreo()).isEqualTo(UPDATED_CORREO);
    }

    @Test
    @Transactional
    void putNonExistingEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();
        estudiante.setId(count.incrementAndGet());

        // Create the Estudiante
        EstudianteDTO estudianteDTO = estudianteMapper.toDto(estudiante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstudianteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estudianteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estudianteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();
        estudiante.setId(count.incrementAndGet());

        // Create the Estudiante
        EstudianteDTO estudianteDTO = estudianteMapper.toDto(estudiante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudianteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estudianteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();
        estudiante.setId(count.incrementAndGet());

        // Create the Estudiante
        EstudianteDTO estudianteDTO = estudianteMapper.toDto(estudiante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudianteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudianteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstudianteWithPatch() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();

        // Update the estudiante using partial update
        Estudiante partialUpdatedEstudiante = new Estudiante();
        partialUpdatedEstudiante.setId(estudiante.getId());

        partialUpdatedEstudiante.estudianteName(UPDATED_ESTUDIANTE_NAME).correo(UPDATED_CORREO);

        restEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstudiante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstudiante))
            )
            .andExpect(status().isOk());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getEstudianteName()).isEqualTo(UPDATED_ESTUDIANTE_NAME);
        assertThat(testEstudiante.getCodigoEstudiante()).isEqualTo(DEFAULT_CODIGO_ESTUDIANTE);
        assertThat(testEstudiante.getCorreo()).isEqualTo(UPDATED_CORREO);
    }

    @Test
    @Transactional
    void fullUpdateEstudianteWithPatch() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();

        // Update the estudiante using partial update
        Estudiante partialUpdatedEstudiante = new Estudiante();
        partialUpdatedEstudiante.setId(estudiante.getId());

        partialUpdatedEstudiante.estudianteName(UPDATED_ESTUDIANTE_NAME).codigoEstudiante(UPDATED_CODIGO_ESTUDIANTE).correo(UPDATED_CORREO);

        restEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstudiante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstudiante))
            )
            .andExpect(status().isOk());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getEstudianteName()).isEqualTo(UPDATED_ESTUDIANTE_NAME);
        assertThat(testEstudiante.getCodigoEstudiante()).isEqualTo(UPDATED_CODIGO_ESTUDIANTE);
        assertThat(testEstudiante.getCorreo()).isEqualTo(UPDATED_CORREO);
    }

    @Test
    @Transactional
    void patchNonExistingEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();
        estudiante.setId(count.incrementAndGet());

        // Create the Estudiante
        EstudianteDTO estudianteDTO = estudianteMapper.toDto(estudiante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estudianteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estudianteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();
        estudiante.setId(count.incrementAndGet());

        // Create the Estudiante
        EstudianteDTO estudianteDTO = estudianteMapper.toDto(estudiante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estudianteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();
        estudiante.setId(count.incrementAndGet());

        // Create the Estudiante
        EstudianteDTO estudianteDTO = estudianteMapper.toDto(estudiante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estudianteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstudiante() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        int databaseSizeBeforeDelete = estudianteRepository.findAll().size();

        // Delete the estudiante
        restEstudianteMockMvc
            .perform(delete(ENTITY_API_URL_ID, estudiante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
