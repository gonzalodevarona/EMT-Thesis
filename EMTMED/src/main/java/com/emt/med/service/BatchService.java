package com.emt.med.service;

import com.emt.med.service.dto.BatchDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.emt.med.domain.Batch}.
 */
public interface BatchService {
    /**
     * Save a batch.
     *
     * @param batchDTO the entity to save.
     * @return the persisted entity.
     */
    BatchDTO save(BatchDTO batchDTO);

    /**
     * Updates a batch.
     *
     * @param batchDTO the entity to update.
     * @return the persisted entity.
     */
    BatchDTO update(BatchDTO batchDTO);

    /**
     * Partially updates a batch.
     *
     * @param batchDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BatchDTO> partialUpdate(BatchDTO batchDTO);

    /**
     * Get all the batches.
     *
     * @return the list of entities.
     */
    List<BatchDTO> findAll();

    /**
     * Get all the batches with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BatchDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" batch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BatchDTO> findOne(Long id);

    /**
     * Delete the "id" batch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
