package com.emt.med.service;

import com.emt.med.service.dto.MedicationBatchDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.emt.med.domain.MedicationBatch}.
 */
public interface MedicationBatchService {
    /**
     * Save a medicationBatch.
     *
     * @param medicationBatch the entity to save.
     * @return the persisted entity.
     */
    MedicationBatchDTO save(MedicationBatchDTO medicationBatch);

    /**
     * Updates a medicationBatch.
     *
     * @param medicationBatch the entity to update.
     * @return the persisted entity.
     */
    MedicationBatchDTO update(MedicationBatchDTO medicationBatch);

    /**
     * Partially updates a medicationBatch.
     *
     * @param medicationBatch the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MedicationBatchDTO> partialUpdate(MedicationBatchDTO medicationBatch);

    /**
     * Get all the medicationBatches.
     *
     * @return the list of entities.
     */
    List<MedicationBatchDTO> findAll();


    /**
     * Get the "id" medicationBatch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicationBatchDTO> findOne(Long id);

    /**
     * Delete the "id" medicationBatch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

