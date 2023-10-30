package com.emt.med.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emt.med.IntegrationTest;
import com.emt.med.domain.Supply;
import com.emt.med.repository.SupplyRepository;
import com.emt.med.service.SupplyService;
import com.emt.med.service.dto.SupplyDTO;
import com.emt.med.service.mapper.SupplyMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SupplyResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SupplyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String ENTITY_API_URL = "/api/supplies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SupplyRepository supplyRepository;

    @Mock
    private SupplyRepository supplyRepositoryMock;

    @Autowired
    private SupplyMapper supplyMapper;

    @Mock
    private SupplyService supplyServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupplyMockMvc;

    private Supply supply;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supply createEntity(EntityManager em) {
        Supply supply = new Supply().name(DEFAULT_NAME).weight(DEFAULT_WEIGHT).quantity(DEFAULT_QUANTITY);
        return supply;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supply createUpdatedEntity(EntityManager em) {
        Supply supply = new Supply().name(UPDATED_NAME).weight(UPDATED_WEIGHT).quantity(UPDATED_QUANTITY);
        return supply;
    }

    @BeforeEach
    public void initTest() {
        supply = createEntity(em);
    }

    @Test
    @Transactional
    void createSupply() throws Exception {
        int databaseSizeBeforeCreate = supplyRepository.findAll().size();
        // Create the Supply
        SupplyDTO supplyDTO = supplyMapper.toDto(supply);
        restSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeCreate + 1);
        Supply testSupply = supplyList.get(supplyList.size() - 1);
        assertThat(testSupply.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupply.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testSupply.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void createSupplyWithExistingId() throws Exception {
        // Create the Supply with an existing ID
        supply.setId(1L);
        SupplyDTO supplyDTO = supplyMapper.toDto(supply);

        int databaseSizeBeforeCreate = supplyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSupplies() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList
        restSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supply.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSuppliesWithEagerRelationshipsIsEnabled() throws Exception {
        when(supplyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSupplyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(supplyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSuppliesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(supplyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSupplyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(supplyRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSupply() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get the supply
        restSupplyMockMvc
            .perform(get(ENTITY_API_URL_ID, supply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supply.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    void getNonExistingSupply() throws Exception {
        // Get the supply
        restSupplyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSupply() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        int databaseSizeBeforeUpdate = supplyRepository.findAll().size();

        // Update the supply
        Supply updatedSupply = supplyRepository.findById(supply.getId()).get();
        // Disconnect from session so that the updates on updatedSupply are not directly saved in db
        em.detach(updatedSupply);
        updatedSupply.name(UPDATED_NAME).weight(UPDATED_WEIGHT).quantity(UPDATED_QUANTITY);
        SupplyDTO supplyDTO = supplyMapper.toDto(updatedSupply);

        restSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplyDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeUpdate);
        Supply testSupply = supplyList.get(supplyList.size() - 1);
        assertThat(testSupply.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupply.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testSupply.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void putNonExistingSupply() throws Exception {
        int databaseSizeBeforeUpdate = supplyRepository.findAll().size();
        supply.setId(count.incrementAndGet());

        // Create the Supply
        SupplyDTO supplyDTO = supplyMapper.toDto(supply);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplyDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSupply() throws Exception {
        int databaseSizeBeforeUpdate = supplyRepository.findAll().size();
        supply.setId(count.incrementAndGet());

        // Create the Supply
        SupplyDTO supplyDTO = supplyMapper.toDto(supply);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSupply() throws Exception {
        int databaseSizeBeforeUpdate = supplyRepository.findAll().size();
        supply.setId(count.incrementAndGet());

        // Create the Supply
        SupplyDTO supplyDTO = supplyMapper.toDto(supply);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplyMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSupplyWithPatch() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        int databaseSizeBeforeUpdate = supplyRepository.findAll().size();

        // Update the supply using partial update
        Supply partialUpdatedSupply = new Supply();
        partialUpdatedSupply.setId(supply.getId());

        partialUpdatedSupply.name(UPDATED_NAME).quantity(UPDATED_QUANTITY);

        restSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupply.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupply))
            )
            .andExpect(status().isOk());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeUpdate);
        Supply testSupply = supplyList.get(supplyList.size() - 1);
        assertThat(testSupply.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupply.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testSupply.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void fullUpdateSupplyWithPatch() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        int databaseSizeBeforeUpdate = supplyRepository.findAll().size();

        // Update the supply using partial update
        Supply partialUpdatedSupply = new Supply();
        partialUpdatedSupply.setId(supply.getId());

        partialUpdatedSupply.name(UPDATED_NAME).weight(UPDATED_WEIGHT).quantity(UPDATED_QUANTITY);

        restSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupply.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupply))
            )
            .andExpect(status().isOk());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeUpdate);
        Supply testSupply = supplyList.get(supplyList.size() - 1);
        assertThat(testSupply.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupply.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testSupply.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void patchNonExistingSupply() throws Exception {
        int databaseSizeBeforeUpdate = supplyRepository.findAll().size();
        supply.setId(count.incrementAndGet());

        // Create the Supply
        SupplyDTO supplyDTO = supplyMapper.toDto(supply);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, supplyDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSupply() throws Exception {
        int databaseSizeBeforeUpdate = supplyRepository.findAll().size();
        supply.setId(count.incrementAndGet());

        // Create the Supply
        SupplyDTO supplyDTO = supplyMapper.toDto(supply);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSupply() throws Exception {
        int databaseSizeBeforeUpdate = supplyRepository.findAll().size();
        supply.setId(count.incrementAndGet());

        // Create the Supply
        SupplyDTO supplyDTO = supplyMapper.toDto(supply);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSupply() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        int databaseSizeBeforeDelete = supplyRepository.findAll().size();

        // Delete the supply
        restSupplyMockMvc
            .perform(delete(ENTITY_API_URL_ID, supply.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
