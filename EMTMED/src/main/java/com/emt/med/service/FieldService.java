package com.emt.med.service;

import com.emt.med.service.dto.FieldDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.emt.med.domain.Field}.
 */
public interface FieldService {
    /**
     * Save a field.
     *
     * @param fieldDTO the entity to save.
     * @return the persisted entity.
     */
    FieldDTO save(FieldDTO fieldDTO);

    /**
     * Updates a field.
     *
     * @param fieldDTO the entity to update.
     * @return the persisted entity.
     */
    FieldDTO update(FieldDTO fieldDTO);

    /**
     * Partially updates a field.
     *
     * @param fieldDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldDTO> partialUpdate(FieldDTO fieldDTO);

    /**
     * Get all the fields.
     *
     * @return the list of entities.
     */
    List<FieldDTO> findAll();

    /**
     * Get the "id" field.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldDTO> findOne(Long id);

    /**
     * Delete the "id" field.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
