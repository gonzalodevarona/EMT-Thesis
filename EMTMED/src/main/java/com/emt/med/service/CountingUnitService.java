package com.emt.med.service;

import com.emt.med.service.dto.CountingUnitDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emt.med.domain.CountingUnit}.
 */
public interface CountingUnitService {
    /**
     * Save a countingUnit.
     *
     * @param countingUnitDTO the entity to save.
     * @return the persisted entity.
     */
    CountingUnitDTO save(CountingUnitDTO countingUnitDTO);

    /**
     * Updates a countingUnit.
     *
     * @param countingUnitDTO the entity to update.
     * @return the persisted entity.
     */
    CountingUnitDTO update(CountingUnitDTO countingUnitDTO);

    /**
     * Partially updates a countingUnit.
     *
     * @param countingUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CountingUnitDTO> partialUpdate(CountingUnitDTO countingUnitDTO);

    /**
     * Get all the countingUnits.
     *
     * @return the list of entities.
     */
    List<CountingUnitDTO> findAll();

    /**
     * Get the "id" countingUnit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CountingUnitDTO> findOne(Long id);

    /**
     * Delete the "id" countingUnit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
