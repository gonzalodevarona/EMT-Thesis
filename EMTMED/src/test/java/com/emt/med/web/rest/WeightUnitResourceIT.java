package com.emt.med.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emt.med.IntegrationTest;
import com.emt.med.domain.WeightUnit;
import com.emt.med.repository.WeightUnitRepository;
import com.emt.med.service.dto.WeightUnitDTO;
import com.emt.med.service.mapper.WeightUnitMapper;
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
 * Integration tests for the {@link WeightUnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WeightUnitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/weight-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WeightUnitRepository weightUnitRepository;

    @Autowired
    private WeightUnitMapper weightUnitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWeightUnitMockMvc;

    private WeightUnit weightUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeightUnit createEntity(EntityManager em) {
        WeightUnit weightUnit = new WeightUnit().name(DEFAULT_NAME);
        return weightUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeightUnit createUpdatedEntity(EntityManager em) {
        WeightUnit weightUnit = new WeightUnit().name(UPDATED_NAME);
        return weightUnit;
    }

    @BeforeEach
    public void initTest() {
        weightUnit = createEntity(em);
    }

    @Test
    @Transactional
    void createWeightUnit() throws Exception {
        int databaseSizeBeforeCreate = weightUnitRepository.findAll().size();
        // Create the WeightUnit
        WeightUnitDTO weightUnitDTO = weightUnitMapper.toDto(weightUnit);
        restWeightUnitMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weightUnitDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WeightUnit in the database
        List<WeightUnit> weightUnitList = weightUnitRepository.findAll();
        assertThat(weightUnitList).hasSize(databaseSizeBeforeCreate + 1);
        WeightUnit testWeightUnit = weightUnitList.get(weightUnitList.size() - 1);
        assertThat(testWeightUnit.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createWeightUnitWithExistingId() throws Exception {
        // Create the WeightUnit with an existing ID
        weightUnit.setId(1L);
        WeightUnitDTO weightUnitDTO = weightUnitMapper.toDto(weightUnit);

        int databaseSizeBeforeCreate = weightUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeightUnitMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weightUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeightUnit in the database
        List<WeightUnit> weightUnitList = weightUnitRepository.findAll();
        assertThat(weightUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWeightUnits() throws Exception {
        // Initialize the database
        weightUnitRepository.saveAndFlush(weightUnit);

        // Get all the weightUnitList
        restWeightUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weightUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getWeightUnit() throws Exception {
        // Initialize the database
        weightUnitRepository.saveAndFlush(weightUnit);

        // Get the weightUnit
        restWeightUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, weightUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(weightUnit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingWeightUnit() throws Exception {
        // Get the weightUnit
        restWeightUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWeightUnit() throws Exception {
        // Initialize the database
        weightUnitRepository.saveAndFlush(weightUnit);

        int databaseSizeBeforeUpdate = weightUnitRepository.findAll().size();

        // Update the weightUnit
        WeightUnit updatedWeightUnit = weightUnitRepository.findById(weightUnit.getId()).get();
        // Disconnect from session so that the updates on updatedWeightUnit are not directly saved in db
        em.detach(updatedWeightUnit);
        updatedWeightUnit.name(UPDATED_NAME);
        WeightUnitDTO weightUnitDTO = weightUnitMapper.toDto(updatedWeightUnit);

        restWeightUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, weightUnitDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weightUnitDTO))
            )
            .andExpect(status().isOk());

        // Validate the WeightUnit in the database
        List<WeightUnit> weightUnitList = weightUnitRepository.findAll();
        assertThat(weightUnitList).hasSize(databaseSizeBeforeUpdate);
        WeightUnit testWeightUnit = weightUnitList.get(weightUnitList.size() - 1);
        assertThat(testWeightUnit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingWeightUnit() throws Exception {
        int databaseSizeBeforeUpdate = weightUnitRepository.findAll().size();
        weightUnit.setId(count.incrementAndGet());

        // Create the WeightUnit
        WeightUnitDTO weightUnitDTO = weightUnitMapper.toDto(weightUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeightUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, weightUnitDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weightUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeightUnit in the database
        List<WeightUnit> weightUnitList = weightUnitRepository.findAll();
        assertThat(weightUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWeightUnit() throws Exception {
        int databaseSizeBeforeUpdate = weightUnitRepository.findAll().size();
        weightUnit.setId(count.incrementAndGet());

        // Create the WeightUnit
        WeightUnitDTO weightUnitDTO = weightUnitMapper.toDto(weightUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weightUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeightUnit in the database
        List<WeightUnit> weightUnitList = weightUnitRepository.findAll();
        assertThat(weightUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWeightUnit() throws Exception {
        int databaseSizeBeforeUpdate = weightUnitRepository.findAll().size();
        weightUnit.setId(count.incrementAndGet());

        // Create the WeightUnit
        WeightUnitDTO weightUnitDTO = weightUnitMapper.toDto(weightUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightUnitMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weightUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WeightUnit in the database
        List<WeightUnit> weightUnitList = weightUnitRepository.findAll();
        assertThat(weightUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWeightUnitWithPatch() throws Exception {
        // Initialize the database
        weightUnitRepository.saveAndFlush(weightUnit);

        int databaseSizeBeforeUpdate = weightUnitRepository.findAll().size();

        // Update the weightUnit using partial update
        WeightUnit partialUpdatedWeightUnit = new WeightUnit();
        partialUpdatedWeightUnit.setId(weightUnit.getId());

        restWeightUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeightUnit.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWeightUnit))
            )
            .andExpect(status().isOk());

        // Validate the WeightUnit in the database
        List<WeightUnit> weightUnitList = weightUnitRepository.findAll();
        assertThat(weightUnitList).hasSize(databaseSizeBeforeUpdate);
        WeightUnit testWeightUnit = weightUnitList.get(weightUnitList.size() - 1);
        assertThat(testWeightUnit.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateWeightUnitWithPatch() throws Exception {
        // Initialize the database
        weightUnitRepository.saveAndFlush(weightUnit);

        int databaseSizeBeforeUpdate = weightUnitRepository.findAll().size();

        // Update the weightUnit using partial update
        WeightUnit partialUpdatedWeightUnit = new WeightUnit();
        partialUpdatedWeightUnit.setId(weightUnit.getId());

        partialUpdatedWeightUnit.name(UPDATED_NAME);

        restWeightUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeightUnit.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWeightUnit))
            )
            .andExpect(status().isOk());

        // Validate the WeightUnit in the database
        List<WeightUnit> weightUnitList = weightUnitRepository.findAll();
        assertThat(weightUnitList).hasSize(databaseSizeBeforeUpdate);
        WeightUnit testWeightUnit = weightUnitList.get(weightUnitList.size() - 1);
        assertThat(testWeightUnit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingWeightUnit() throws Exception {
        int databaseSizeBeforeUpdate = weightUnitRepository.findAll().size();
        weightUnit.setId(count.incrementAndGet());

        // Create the WeightUnit
        WeightUnitDTO weightUnitDTO = weightUnitMapper.toDto(weightUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeightUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, weightUnitDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(weightUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeightUnit in the database
        List<WeightUnit> weightUnitList = weightUnitRepository.findAll();
        assertThat(weightUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWeightUnit() throws Exception {
        int databaseSizeBeforeUpdate = weightUnitRepository.findAll().size();
        weightUnit.setId(count.incrementAndGet());

        // Create the WeightUnit
        WeightUnitDTO weightUnitDTO = weightUnitMapper.toDto(weightUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(weightUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeightUnit in the database
        List<WeightUnit> weightUnitList = weightUnitRepository.findAll();
        assertThat(weightUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWeightUnit() throws Exception {
        int databaseSizeBeforeUpdate = weightUnitRepository.findAll().size();
        weightUnit.setId(count.incrementAndGet());

        // Create the WeightUnit
        WeightUnitDTO weightUnitDTO = weightUnitMapper.toDto(weightUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightUnitMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(weightUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WeightUnit in the database
        List<WeightUnit> weightUnitList = weightUnitRepository.findAll();
        assertThat(weightUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWeightUnit() throws Exception {
        // Initialize the database
        weightUnitRepository.saveAndFlush(weightUnit);

        int databaseSizeBeforeDelete = weightUnitRepository.findAll().size();

        // Delete the weightUnit
        restWeightUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, weightUnit.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WeightUnit> weightUnitList = weightUnitRepository.findAll();
        assertThat(weightUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
