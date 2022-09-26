package co.edu.usbcali.ingesoft.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.usbcali.ingesoft.IntegrationTest;
import co.edu.usbcali.ingesoft.domain.Actividad;
import co.edu.usbcali.ingesoft.domain.enumeration.NombreFormato;
import co.edu.usbcali.ingesoft.repository.ActividadRepository;
import co.edu.usbcali.ingesoft.service.dto.ActividadDTO;
import co.edu.usbcali.ingesoft.service.mapper.ActividadMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ActividadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActividadResourceIT {

    private static final String DEFAULT_ACTIVIDAD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVIDAD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final NombreFormato DEFAULT_FORMATO = NombreFormato.QUIZ;
    private static final NombreFormato UPDATED_FORMATO = NombreFormato.ACTIVIDAD;

    private static final String ENTITY_API_URL = "/api/actividads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private ActividadMapper actividadMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActividadMockMvc;

    private Actividad actividad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Actividad createEntity(EntityManager em) {
        Actividad actividad = new Actividad()
            .actividadName(DEFAULT_ACTIVIDAD_NAME)
            .description(DEFAULT_DESCRIPTION)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .formato(DEFAULT_FORMATO);
        return actividad;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Actividad createUpdatedEntity(EntityManager em) {
        Actividad actividad = new Actividad()
            .actividadName(UPDATED_ACTIVIDAD_NAME)
            .description(UPDATED_DESCRIPTION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .formato(UPDATED_FORMATO);
        return actividad;
    }

    @BeforeEach
    public void initTest() {
        actividad = createEntity(em);
    }

    @Test
    @Transactional
    void createActividad() throws Exception {
        int databaseSizeBeforeCreate = actividadRepository.findAll().size();
        // Create the Actividad
        ActividadDTO actividadDTO = actividadMapper.toDto(actividad);
        restActividadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actividadDTO)))
            .andExpect(status().isCreated());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeCreate + 1);
        Actividad testActividad = actividadList.get(actividadList.size() - 1);
        assertThat(testActividad.getActividadName()).isEqualTo(DEFAULT_ACTIVIDAD_NAME);
        assertThat(testActividad.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testActividad.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testActividad.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testActividad.getFormato()).isEqualTo(DEFAULT_FORMATO);
    }

    @Test
    @Transactional
    void createActividadWithExistingId() throws Exception {
        // Create the Actividad with an existing ID
        actividad.setId(1L);
        ActividadDTO actividadDTO = actividadMapper.toDto(actividad);

        int databaseSizeBeforeCreate = actividadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActividadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actividadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActividads() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

        // Get all the actividadList
        restActividadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actividad.getId().intValue())))
            .andExpect(jsonPath("$.[*].actividadName").value(hasItem(DEFAULT_ACTIVIDAD_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].formato").value(hasItem(DEFAULT_FORMATO.toString())));
    }

    @Test
    @Transactional
    void getActividad() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

        // Get the actividad
        restActividadMockMvc
            .perform(get(ENTITY_API_URL_ID, actividad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actividad.getId().intValue()))
            .andExpect(jsonPath("$.actividadName").value(DEFAULT_ACTIVIDAD_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.formato").value(DEFAULT_FORMATO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingActividad() throws Exception {
        // Get the actividad
        restActividadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActividad() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

        int databaseSizeBeforeUpdate = actividadRepository.findAll().size();

        // Update the actividad
        Actividad updatedActividad = actividadRepository.findById(actividad.getId()).get();
        // Disconnect from session so that the updates on updatedActividad are not directly saved in db
        em.detach(updatedActividad);
        updatedActividad
            .actividadName(UPDATED_ACTIVIDAD_NAME)
            .description(UPDATED_DESCRIPTION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .formato(UPDATED_FORMATO);
        ActividadDTO actividadDTO = actividadMapper.toDto(updatedActividad);

        restActividadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, actividadDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actividadDTO))
            )
            .andExpect(status().isOk());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeUpdate);
        Actividad testActividad = actividadList.get(actividadList.size() - 1);
        assertThat(testActividad.getActividadName()).isEqualTo(UPDATED_ACTIVIDAD_NAME);
        assertThat(testActividad.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testActividad.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testActividad.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testActividad.getFormato()).isEqualTo(UPDATED_FORMATO);
    }

    @Test
    @Transactional
    void putNonExistingActividad() throws Exception {
        int databaseSizeBeforeUpdate = actividadRepository.findAll().size();
        actividad.setId(count.incrementAndGet());

        // Create the Actividad
        ActividadDTO actividadDTO = actividadMapper.toDto(actividad);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActividadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, actividadDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actividadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActividad() throws Exception {
        int databaseSizeBeforeUpdate = actividadRepository.findAll().size();
        actividad.setId(count.incrementAndGet());

        // Create the Actividad
        ActividadDTO actividadDTO = actividadMapper.toDto(actividad);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActividadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actividadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActividad() throws Exception {
        int databaseSizeBeforeUpdate = actividadRepository.findAll().size();
        actividad.setId(count.incrementAndGet());

        // Create the Actividad
        ActividadDTO actividadDTO = actividadMapper.toDto(actividad);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActividadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actividadDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActividadWithPatch() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

        int databaseSizeBeforeUpdate = actividadRepository.findAll().size();

        // Update the actividad using partial update
        Actividad partialUpdatedActividad = new Actividad();
        partialUpdatedActividad.setId(actividad.getId());

        partialUpdatedActividad.fechaInicio(UPDATED_FECHA_INICIO);

        restActividadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActividad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActividad))
            )
            .andExpect(status().isOk());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeUpdate);
        Actividad testActividad = actividadList.get(actividadList.size() - 1);
        assertThat(testActividad.getActividadName()).isEqualTo(DEFAULT_ACTIVIDAD_NAME);
        assertThat(testActividad.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testActividad.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testActividad.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testActividad.getFormato()).isEqualTo(DEFAULT_FORMATO);
    }

    @Test
    @Transactional
    void fullUpdateActividadWithPatch() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

        int databaseSizeBeforeUpdate = actividadRepository.findAll().size();

        // Update the actividad using partial update
        Actividad partialUpdatedActividad = new Actividad();
        partialUpdatedActividad.setId(actividad.getId());

        partialUpdatedActividad
            .actividadName(UPDATED_ACTIVIDAD_NAME)
            .description(UPDATED_DESCRIPTION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .formato(UPDATED_FORMATO);

        restActividadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActividad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActividad))
            )
            .andExpect(status().isOk());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeUpdate);
        Actividad testActividad = actividadList.get(actividadList.size() - 1);
        assertThat(testActividad.getActividadName()).isEqualTo(UPDATED_ACTIVIDAD_NAME);
        assertThat(testActividad.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testActividad.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testActividad.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testActividad.getFormato()).isEqualTo(UPDATED_FORMATO);
    }

    @Test
    @Transactional
    void patchNonExistingActividad() throws Exception {
        int databaseSizeBeforeUpdate = actividadRepository.findAll().size();
        actividad.setId(count.incrementAndGet());

        // Create the Actividad
        ActividadDTO actividadDTO = actividadMapper.toDto(actividad);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActividadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, actividadDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(actividadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActividad() throws Exception {
        int databaseSizeBeforeUpdate = actividadRepository.findAll().size();
        actividad.setId(count.incrementAndGet());

        // Create the Actividad
        ActividadDTO actividadDTO = actividadMapper.toDto(actividad);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActividadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(actividadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActividad() throws Exception {
        int databaseSizeBeforeUpdate = actividadRepository.findAll().size();
        actividad.setId(count.incrementAndGet());

        // Create the Actividad
        ActividadDTO actividadDTO = actividadMapper.toDto(actividad);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActividadMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(actividadDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActividad() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

        int databaseSizeBeforeDelete = actividadRepository.findAll().size();

        // Delete the actividad
        restActividadMockMvc
            .perform(delete(ENTITY_API_URL_ID, actividad.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
