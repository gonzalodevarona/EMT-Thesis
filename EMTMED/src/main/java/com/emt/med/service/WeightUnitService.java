package com.emt.med.service;

import com.emt.med.service.dto.WeightUnitDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emt.med.domain.WeightUnit}.
 */
public interface WeightUnitService {
    /**
     * Save a weightUnit.
     *
     * @param weightUnitDTO the entity to save.
     * @return the persisted entity.
     */
    WeightUnitDTO save(WeightUnitDTO weightUnitDTO);

    /**
     * Updates a weightUnit.
     *
     * @param weightUnitDTO the entity to update.
     * @return the persisted entity.
     */
    WeightUnitDTO update(WeightUnitDTO weightUnitDTO);

    /**
     * Partially updates a weightUnit.
     *
     * @param weightUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WeightUnitDTO> partialUpdate(WeightUnitDTO weightUnitDTO);

    /**
     * Get all the weightUnits.
     *
     * @return the list of entities.
     */
    List<WeightUnitDTO> findAll();

    /**
     * Get the "id" weightUnit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WeightUnitDTO> findOne(Long id);

    /**
     * Delete the "id" weightUnit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
