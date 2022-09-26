package co.edu.usbcali.ingesoft.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.usbcali.ingesoft.IntegrationTest;
import co.edu.usbcali.ingesoft.domain.Desarrollo;
import co.edu.usbcali.ingesoft.repository.DesarrolloRepository;
import co.edu.usbcali.ingesoft.service.dto.DesarrolloDTO;
import co.edu.usbcali.ingesoft.service.mapper.DesarrolloMapper;
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
 * Integration tests for the {@link DesarrolloResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DesarrolloResourceIT {

    private static final Boolean DEFAULT_TERMINADO = false;
    private static final Boolean UPDATED_TERMINADO = true;

    private static final Double DEFAULT_NOTA = 1D;
    private static final Double UPDATED_NOTA = 2D;

    private static final String ENTITY_API_URL = "/api/desarrollos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DesarrolloRepository desarrolloRepository;

    @Autowired
    private DesarrolloMapper desarrolloMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDesarrolloMockMvc;

    private Desarrollo desarrollo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Desarrollo createEntity(EntityManager em) {
        Desarrollo desarrollo = new Desarrollo().terminado(DEFAULT_TERMINADO).nota(DEFAULT_NOTA);
        return desarrollo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Desarrollo createUpdatedEntity(EntityManager em) {
        Desarrollo desarrollo = new Desarrollo().terminado(UPDATED_TERMINADO).nota(UPDATED_NOTA);
        return desarrollo;
    }

    @BeforeEach
    public void initTest() {
        desarrollo = createEntity(em);
    }

    @Test
    @Transactional
    void createDesarrollo() throws Exception {
        int databaseSizeBeforeCreate = desarrolloRepository.findAll().size();
        // Create the Desarrollo
        DesarrolloDTO desarrolloDTO = desarrolloMapper.toDto(desarrollo);
        restDesarrolloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(desarrolloDTO)))
            .andExpect(status().isCreated());

        // Validate the Desarrollo in the database
        List<Desarrollo> desarrolloList = desarrolloRepository.findAll();
        assertThat(desarrolloList).hasSize(databaseSizeBeforeCreate + 1);
        Desarrollo testDesarrollo = desarrolloList.get(desarrolloList.size() - 1);
        assertThat(testDesarrollo.getTerminado()).isEqualTo(DEFAULT_TERMINADO);
        assertThat(testDesarrollo.getNota()).isEqualTo(DEFAULT_NOTA);
    }

    @Test
    @Transactional
    void createDesarrolloWithExistingId() throws Exception {
        // Create the Desarrollo with an existing ID
        desarrollo.setId(1L);
        DesarrolloDTO desarrolloDTO = desarrolloMapper.toDto(desarrollo);

        int databaseSizeBeforeCreate = desarrolloRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesarrolloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(desarrolloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Desarrollo in the database
        List<Desarrollo> desarrolloList = desarrolloRepository.findAll();
        assertThat(desarrolloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDesarrollos() throws Exception {
        // Initialize the database
        desarrolloRepository.saveAndFlush(desarrollo);

        // Get all the desarrolloList
        restDesarrolloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(desarrollo.getId().intValue())))
            .andExpect(jsonPath("$.[*].terminado").value(hasItem(DEFAULT_TERMINADO.booleanValue())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA.doubleValue())));
    }

    @Test
    @Transactional
    void getDesarrollo() throws Exception {
        // Initialize the database
        desarrolloRepository.saveAndFlush(desarrollo);

        // Get the desarrollo
        restDesarrolloMockMvc
            .perform(get(ENTITY_API_URL_ID, desarrollo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(desarrollo.getId().intValue()))
            .andExpect(jsonPath("$.terminado").value(DEFAULT_TERMINADO.booleanValue()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingDesarrollo() throws Exception {
        // Get the desarrollo
        restDesarrolloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDesarrollo() throws Exception {
        // Initialize the database
        desarrolloRepository.saveAndFlush(desarrollo);

        int databaseSizeBeforeUpdate = desarrolloRepository.findAll().size();

        // Update the desarrollo
        Desarrollo updatedDesarrollo = desarrolloRepository.findById(desarrollo.getId()).get();
        // Disconnect from session so that the updates on updatedDesarrollo are not directly saved in db
        em.detach(updatedDesarrollo);
        updatedDesarrollo.terminado(UPDATED_TERMINADO).nota(UPDATED_NOTA);
        DesarrolloDTO desarrolloDTO = desarrolloMapper.toDto(updatedDesarrollo);

        restDesarrolloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, desarrolloDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(desarrolloDTO))
            )
            .andExpect(status().isOk());

        // Validate the Desarrollo in the database
        List<Desarrollo> desarrolloList = desarrolloRepository.findAll();
        assertThat(desarrolloList).hasSize(databaseSizeBeforeUpdate);
        Desarrollo testDesarrollo = desarrolloList.get(desarrolloList.size() - 1);
        assertThat(testDesarrollo.getTerminado()).isEqualTo(UPDATED_TERMINADO);
        assertThat(testDesarrollo.getNota()).isEqualTo(UPDATED_NOTA);
    }

    @Test
    @Transactional
    void putNonExistingDesarrollo() throws Exception {
        int databaseSizeBeforeUpdate = desarrolloRepository.findAll().size();
        desarrollo.setId(count.incrementAndGet());

        // Create the Desarrollo
        DesarrolloDTO desarrolloDTO = desarrolloMapper.toDto(desarrollo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesarrolloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, desarrolloDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(desarrolloDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Desarrollo in the database
        List<Desarrollo> desarrolloList = desarrolloRepository.findAll();
        assertThat(desarrolloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDesarrollo() throws Exception {
        int databaseSizeBeforeUpdate = desarrolloRepository.findAll().size();
        desarrollo.setId(count.incrementAndGet());

        // Create the Desarrollo
        DesarrolloDTO desarrolloDTO = desarrolloMapper.toDto(desarrollo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesarrolloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(desarrolloDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Desarrollo in the database
        List<Desarrollo> desarrolloList = desarrolloRepository.findAll();
        assertThat(desarrolloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDesarrollo() throws Exception {
        int databaseSizeBeforeUpdate = desarrolloRepository.findAll().size();
        desarrollo.setId(count.incrementAndGet());

        // Create the Desarrollo
        DesarrolloDTO desarrolloDTO = desarrolloMapper.toDto(desarrollo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesarrolloMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(desarrolloDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Desarrollo in the database
        List<Desarrollo> desarrolloList = desarrolloRepository.findAll();
        assertThat(desarrolloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDesarrolloWithPatch() throws Exception {
        // Initialize the database
        desarrolloRepository.saveAndFlush(desarrollo);

        int databaseSizeBeforeUpdate = desarrolloRepository.findAll().size();

        // Update the desarrollo using partial update
        Desarrollo partialUpdatedDesarrollo = new Desarrollo();
        partialUpdatedDesarrollo.setId(desarrollo.getId());

        partialUpdatedDesarrollo.nota(UPDATED_NOTA);

        restDesarrolloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesarrollo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesarrollo))
            )
            .andExpect(status().isOk());

        // Validate the Desarrollo in the database
        List<Desarrollo> desarrolloList = desarrolloRepository.findAll();
        assertThat(desarrolloList).hasSize(databaseSizeBeforeUpdate);
        Desarrollo testDesarrollo = desarrolloList.get(desarrolloList.size() - 1);
        assertThat(testDesarrollo.getTerminado()).isEqualTo(DEFAULT_TERMINADO);
        assertThat(testDesarrollo.getNota()).isEqualTo(UPDATED_NOTA);
    }

    @Test
    @Transactional
    void fullUpdateDesarrolloWithPatch() throws Exception {
        // Initialize the database
        desarrolloRepository.saveAndFlush(desarrollo);

        int databaseSizeBeforeUpdate = desarrolloRepository.findAll().size();

        // Update the desarrollo using partial update
        Desarrollo partialUpdatedDesarrollo = new Desarrollo();
        partialUpdatedDesarrollo.setId(desarrollo.getId());

        partialUpdatedDesarrollo.terminado(UPDATED_TERMINADO).nota(UPDATED_NOTA);

        restDesarrolloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesarrollo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesarrollo))
            )
            .andExpect(status().isOk());

        // Validate the Desarrollo in the database
        List<Desarrollo> desarrolloList = desarrolloRepository.findAll();
        assertThat(desarrolloList).hasSize(databaseSizeBeforeUpdate);
        Desarrollo testDesarrollo = desarrolloList.get(desarrolloList.size() - 1);
        assertThat(testDesarrollo.getTerminado()).isEqualTo(UPDATED_TERMINADO);
        assertThat(testDesarrollo.getNota()).isEqualTo(UPDATED_NOTA);
    }

    @Test
    @Transactional
    void patchNonExistingDesarrollo() throws Exception {
        int databaseSizeBeforeUpdate = desarrolloRepository.findAll().size();
        desarrollo.setId(count.incrementAndGet());

        // Create the Desarrollo
        DesarrolloDTO desarrolloDTO = desarrolloMapper.toDto(desarrollo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesarrolloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, desarrolloDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(desarrolloDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Desarrollo in the database
        List<Desarrollo> desarrolloList = desarrolloRepository.findAll();
        assertThat(desarrolloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDesarrollo() throws Exception {
        int databaseSizeBeforeUpdate = desarrolloRepository.findAll().size();
        desarrollo.setId(count.incrementAndGet());

        // Create the Desarrollo
        DesarrolloDTO desarrolloDTO = desarrolloMapper.toDto(desarrollo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesarrolloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(desarrolloDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Desarrollo in the database
        List<Desarrollo> desarrolloList = desarrolloRepository.findAll();
        assertThat(desarrolloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDesarrollo() throws Exception {
        int databaseSizeBeforeUpdate = desarrolloRepository.findAll().size();
        desarrollo.setId(count.incrementAndGet());

        // Create the Desarrollo
        DesarrolloDTO desarrolloDTO = desarrolloMapper.toDto(desarrollo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesarrolloMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(desarrolloDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Desarrollo in the database
        List<Desarrollo> desarrolloList = desarrolloRepository.findAll();
        assertThat(desarrolloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDesarrollo() throws Exception {
        // Initialize the database
        desarrolloRepository.saveAndFlush(desarrollo);

        int databaseSizeBeforeDelete = desarrolloRepository.findAll().size();

        // Delete the desarrollo
        restDesarrolloMockMvc
            .perform(delete(ENTITY_API_URL_ID, desarrollo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Desarrollo> desarrolloList = desarrolloRepository.findAll();
        assertThat(desarrolloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
