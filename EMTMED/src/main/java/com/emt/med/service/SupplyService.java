package com.emt.med.service;

import com.emt.med.service.dto.SupplyDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.emt.med.domain.Supply}.
 */
public interface SupplyService {
    /**
     * Save a supply.
     *
     * @param supplyDTO the entity to save.
     * @return the persisted entity.
     */
    SupplyDTO save(SupplyDTO supplyDTO);

    /**
     * Updates a supply.
     *
     * @param supplyDTO the entity to update.
     * @return the persisted entity.
     */
    SupplyDTO update(SupplyDTO supplyDTO);

    /**
     * Partially updates a supply.
     *
     * @param supplyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SupplyDTO> partialUpdate(SupplyDTO supplyDTO);

    /**
     * Get all the supplies.
     *
     * @return the list of entities.
     */
    List<SupplyDTO> findAll();

    /**
     * Get all the supplies with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupplyDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" supply.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupplyDTO> findOne(Long id);

    /**
     * Delete the "id" supply.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
