package com.emt.med.web.rest;

import com.emt.med.repository.MedicationBatchRepository;
import com.emt.med.service.MedicationBatchService;
import com.emt.med.service.dto.MedicationBatchDTO;
import com.emt.med.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.emt.med.domain.MedicationBatch}.
 */
@RestController
@RequestMapping("/api")
public class MedicationBatchResource {


    private final Logger log = LoggerFactory.getLogger(MedicationBatchResource.class);

    private static final String ENTITY_NAME = "emtmedMedicationBatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    private final MedicationBatchService medicationBatchService;

    private final MedicationBatchRepository medicationBatchRepository;

    public MedicationBatchResource(MedicationBatchService medicationBatchService, MedicationBatchRepository medicationBatchRepository) {
        this.medicationBatchService = medicationBatchService;
        this.medicationBatchRepository = medicationBatchRepository;
    }

    /**
     * {@code POST  /medicationBatches} : Create a new medicationBatch.
     *
     * @param medicationBatchDTO the medicationBatchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicationBatchDTO, or with status {@code 400 (Bad Request)} if the medicationBatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medication-batches")
    public ResponseEntity<MedicationBatchDTO> createMedicationBatch(@RequestBody MedicationBatchDTO medicationBatchDTO) throws URISyntaxException {
        log.debug("REST request to save MedicationBatch : {}", medicationBatchDTO);
        if (medicationBatchDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicationBatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicationBatchDTO result = medicationBatchService.save(medicationBatchDTO);
        return ResponseEntity
            .created(new URI("/api/medicationBatches/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /medicationBatches/:id} : Updates an existing medicationBatch.
     *
     * @param id the id of the medicationBatchDTO to save.
     * @param medicationBatchDTO the medicationBatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicationBatchDTO,
     * or with status {@code 400 (Bad Request)} if the medicationBatchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicationBatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medication-batches/{id}")
    public ResponseEntity<MedicationBatchDTO> updateMedicationBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicationBatchDTO medicationBatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MedicationBatch : {}, {}", id, medicationBatchDTO);
        if (medicationBatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicationBatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicationBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MedicationBatchDTO result = medicationBatchService.update(medicationBatchDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /medicationBatches/:id} : Partial updates given fields of an existing medicationBatch, field will ignore if it is null
     *
     * @param id the id of the medicationBatchDTO to save.
     * @param medicationBatchDTO the medicationBatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicationBatchDTO,
     * or with status {@code 400 (Bad Request)} if the medicationBatchDTO is not valid,
     * or with status {@code 404 (Not Found)} if the medicationBatchDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the medicationBatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/medication-batches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MedicationBatchDTO> partialUpdateMedicationBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicationBatchDTO medicationBatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MedicationBatch partially : {}, {}", id, medicationBatchDTO);
        if (medicationBatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicationBatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicationBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MedicationBatchDTO> result = medicationBatchService.partialUpdate(medicationBatchDTO);

        return ResponseUtil.wrapOrNotFound(
            result
        );
    }

    /**
     * {@code GET  /medicationBatches} : get all the medicationBatches.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicationBatches in body.
     */
    @GetMapping("/medication-batches")
    public List<MedicationBatchDTO> getAllMedicationBatches(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all MedicationBatches");
        return medicationBatchService.findAll();
    }

    /**
     * {@code GET  /medicationBatches/:id} : get the "id" medicationBatch.
     *
     * @param id the id of the medicationBatchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicationBatchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medication-batches/{id}")
    public ResponseEntity<MedicationBatchDTO> getMedicationBatch(@PathVariable Long id) {
        log.debug("REST request to get MedicationBatch : {}", id);
        Optional<MedicationBatchDTO> medicationBatchDTO = medicationBatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicationBatchDTO);
    }

    /**
     * {@code DELETE  /medicationBatches/:id} : delete the "id" medicationBatch.
     *
     * @param id the id of the medicationBatchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medication-batches/{id}")
    public ResponseEntity<Void> deleteMedicationBatch(@PathVariable Long id) {
        log.debug("REST request to delete MedicationBatch : {}", id);
        medicationBatchService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
