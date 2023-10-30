package com.emt.med.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emt.med.IntegrationTest;
import com.emt.med.domain.CountingUnit;
import com.emt.med.repository.CountingUnitRepository;
import com.emt.med.service.dto.CountingUnitDTO;
import com.emt.med.service.mapper.CountingUnitMapper;
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
 * Integration tests for the {@link CountingUnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CountingUnitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/counting-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CountingUnitRepository countingUnitRepository;

    @Autowired
    private CountingUnitMapper countingUnitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountingUnitMockMvc;

    private CountingUnit countingUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountingUnit createEntity(EntityManager em) {
        CountingUnit countingUnit = new CountingUnit().name(DEFAULT_NAME);
        return countingUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CountingUnit createUpdatedEntity(EntityManager em) {
        CountingUnit countingUnit = new CountingUnit().name(UPDATED_NAME);
        return countingUnit;
    }

    @BeforeEach
    public void initTest() {
        countingUnit = createEntity(em);
    }

    @Test
    @Transactional
    void createCountingUnit() throws Exception {
        int databaseSizeBeforeCreate = countingUnitRepository.findAll().size();
        // Create the CountingUnit
        CountingUnitDTO countingUnitDTO = countingUnitMapper.toDto(countingUnit);
        restCountingUnitMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countingUnitDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CountingUnit in the database
        List<CountingUnit> countingUnitList = countingUnitRepository.findAll();
        assertThat(countingUnitList).hasSize(databaseSizeBeforeCreate + 1);
        CountingUnit testCountingUnit = countingUnitList.get(countingUnitList.size() - 1);
        assertThat(testCountingUnit.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCountingUnitWithExistingId() throws Exception {
        // Create the CountingUnit with an existing ID
        countingUnit.setId(1L);
        CountingUnitDTO countingUnitDTO = countingUnitMapper.toDto(countingUnit);

        int databaseSizeBeforeCreate = countingUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountingUnitMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countingUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountingUnit in the database
        List<CountingUnit> countingUnitList = countingUnitRepository.findAll();
        assertThat(countingUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCountingUnits() throws Exception {
        // Initialize the database
        countingUnitRepository.saveAndFlush(countingUnit);

        // Get all the countingUnitList
        restCountingUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countingUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCountingUnit() throws Exception {
        // Initialize the database
        countingUnitRepository.saveAndFlush(countingUnit);

        // Get the countingUnit
        restCountingUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, countingUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(countingUnit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCountingUnit() throws Exception {
        // Get the countingUnit
        restCountingUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCountingUnit() throws Exception {
        // Initialize the database
        countingUnitRepository.saveAndFlush(countingUnit);

        int databaseSizeBeforeUpdate = countingUnitRepository.findAll().size();

        // Update the countingUnit
        CountingUnit updatedCountingUnit = countingUnitRepository.findById(countingUnit.getId()).get();
        // Disconnect from session so that the updates on updatedCountingUnit are not directly saved in db
        em.detach(updatedCountingUnit);
        updatedCountingUnit.name(UPDATED_NAME);
        CountingUnitDTO countingUnitDTO = countingUnitMapper.toDto(updatedCountingUnit);

        restCountingUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countingUnitDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countingUnitDTO))
            )
            .andExpect(status().isOk());

        // Validate the CountingUnit in the database
        List<CountingUnit> countingUnitList = countingUnitRepository.findAll();
        assertThat(countingUnitList).hasSize(databaseSizeBeforeUpdate);
        CountingUnit testCountingUnit = countingUnitList.get(countingUnitList.size() - 1);
        assertThat(testCountingUnit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCountingUnit() throws Exception {
        int databaseSizeBeforeUpdate = countingUnitRepository.findAll().size();
        countingUnit.setId(count.incrementAndGet());

        // Create the CountingUnit
        CountingUnitDTO countingUnitDTO = countingUnitMapper.toDto(countingUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountingUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countingUnitDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countingUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountingUnit in the database
        List<CountingUnit> countingUnitList = countingUnitRepository.findAll();
        assertThat(countingUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCountingUnit() throws Exception {
        int databaseSizeBeforeUpdate = countingUnitRepository.findAll().size();
        countingUnit.setId(count.incrementAndGet());

        // Create the CountingUnit
        CountingUnitDTO countingUnitDTO = countingUnitMapper.toDto(countingUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountingUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countingUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountingUnit in the database
        List<CountingUnit> countingUnitList = countingUnitRepository.findAll();
        assertThat(countingUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCountingUnit() throws Exception {
        int databaseSizeBeforeUpdate = countingUnitRepository.findAll().size();
        countingUnit.setId(count.incrementAndGet());

        // Create the CountingUnit
        CountingUnitDTO countingUnitDTO = countingUnitMapper.toDto(countingUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountingUnitMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countingUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CountingUnit in the database
        List<CountingUnit> countingUnitList = countingUnitRepository.findAll();
        assertThat(countingUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCountingUnitWithPatch() throws Exception {
        // Initialize the database
        countingUnitRepository.saveAndFlush(countingUnit);

        int databaseSizeBeforeUpdate = countingUnitRepository.findAll().size();

        // Update the countingUnit using partial update
        CountingUnit partialUpdatedCountingUnit = new CountingUnit();
        partialUpdatedCountingUnit.setId(countingUnit.getId());

        partialUpdatedCountingUnit.name(UPDATED_NAME);

        restCountingUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountingUnit.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountingUnit))
            )
            .andExpect(status().isOk());

        // Validate the CountingUnit in the database
        List<CountingUnit> countingUnitList = countingUnitRepository.findAll();
        assertThat(countingUnitList).hasSize(databaseSizeBeforeUpdate);
        CountingUnit testCountingUnit = countingUnitList.get(countingUnitList.size() - 1);
        assertThat(testCountingUnit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCountingUnitWithPatch() throws Exception {
        // Initialize the database
        countingUnitRepository.saveAndFlush(countingUnit);

        int databaseSizeBeforeUpdate = countingUnitRepository.findAll().size();

        // Update the countingUnit using partial update
        CountingUnit partialUpdatedCountingUnit = new CountingUnit();
        partialUpdatedCountingUnit.setId(countingUnit.getId());

        partialUpdatedCountingUnit.name(UPDATED_NAME);

        restCountingUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountingUnit.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountingUnit))
            )
            .andExpect(status().isOk());

        // Validate the CountingUnit in the database
        List<CountingUnit> countingUnitList = countingUnitRepository.findAll();
        assertThat(countingUnitList).hasSize(databaseSizeBeforeUpdate);
        CountingUnit testCountingUnit = countingUnitList.get(countingUnitList.size() - 1);
        assertThat(testCountingUnit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCountingUnit() throws Exception {
        int databaseSizeBeforeUpdate = countingUnitRepository.findAll().size();
        countingUnit.setId(count.incrementAndGet());

        // Create the CountingUnit
        CountingUnitDTO countingUnitDTO = countingUnitMapper.toDto(countingUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountingUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, countingUnitDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(countingUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountingUnit in the database
        List<CountingUnit> countingUnitList = countingUnitRepository.findAll();
        assertThat(countingUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCountingUnit() throws Exception {
        int databaseSizeBeforeUpdate = countingUnitRepository.findAll().size();
        countingUnit.setId(count.incrementAndGet());

        // Create the CountingUnit
        CountingUnitDTO countingUnitDTO = countingUnitMapper.toDto(countingUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountingUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(countingUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CountingUnit in the database
        List<CountingUnit> countingUnitList = countingUnitRepository.findAll();
        assertThat(countingUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCountingUnit() throws Exception {
        int databaseSizeBeforeUpdate = countingUnitRepository.findAll().size();
        countingUnit.setId(count.incrementAndGet());

        // Create the CountingUnit
        CountingUnitDTO countingUnitDTO = countingUnitMapper.toDto(countingUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountingUnitMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(countingUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CountingUnit in the database
        List<CountingUnit> countingUnitList = countingUnitRepository.findAll();
        assertThat(countingUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCountingUnit() throws Exception {
        // Initialize the database
        countingUnitRepository.saveAndFlush(countingUnit);

        int databaseSizeBeforeDelete = countingUnitRepository.findAll().size();

        // Delete the countingUnit
        restCountingUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, countingUnit.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CountingUnit> countingUnitList = countingUnitRepository.findAll();
        assertThat(countingUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
