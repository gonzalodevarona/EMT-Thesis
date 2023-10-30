package com.emt.med.web.rest;

import com.emt.med.repository.BatchRepository;
import com.emt.med.service.BatchService;
import com.emt.med.service.dto.BatchDTO;
import com.emt.med.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.emt.med.domain.Batch}.
 */
@RestController
@RequestMapping("/api")
public class BatchResource {

    private final Logger log = LoggerFactory.getLogger(BatchResource.class);

    private static final String ENTITY_NAME = "emtmedBatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BatchService batchService;

    private final BatchRepository batchRepository;

    public BatchResource(BatchService batchService, BatchRepository batchRepository) {
        this.batchService = batchService;
        this.batchRepository = batchRepository;
    }

    /**
     * {@code POST  /batches} : Create a new batch.
     *
     * @param batchDTO the batchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new batchDTO, or with status {@code 400 (Bad Request)} if the batch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/batches")
    public ResponseEntity<BatchDTO> createBatch(@RequestBody BatchDTO batchDTO) throws URISyntaxException {
        log.debug("REST request to save Batch : {}", batchDTO);
        if (batchDTO.getId() != null) {
            throw new BadRequestAlertException("A new batch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BatchDTO result = batchService.save(batchDTO);
        return ResponseEntity
            .created(new URI("/api/batches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /batches/:id} : Updates an existing batch.
     *
     * @param id the id of the batchDTO to save.
     * @param batchDTO the batchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchDTO,
     * or with status {@code 400 (Bad Request)} if the batchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the batchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/batches/{id}")
    public ResponseEntity<BatchDTO> updateBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BatchDTO batchDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Batch : {}, {}", id, batchDTO);
        if (batchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BatchDTO result = batchService.update(batchDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, batchDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /batches/:id} : Partial updates given fields of an existing batch, field will ignore if it is null
     *
     * @param id the id of the batchDTO to save.
     * @param batchDTO the batchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchDTO,
     * or with status {@code 400 (Bad Request)} if the batchDTO is not valid,
     * or with status {@code 404 (Not Found)} if the batchDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the batchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/batches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BatchDTO> partialUpdateBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BatchDTO batchDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Batch partially : {}, {}", id, batchDTO);
        if (batchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BatchDTO> result = batchService.partialUpdate(batchDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, batchDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /batches} : get all the batches.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of batches in body.
     */
    @GetMapping("/batches")
    public List<BatchDTO> getAllBatches(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Batches");
        return batchService.findAll();
    }

    /**
     * {@code GET  /batches/:id} : get the "id" batch.
     *
     * @param id the id of the batchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the batchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/batches/{id}")
    public ResponseEntity<BatchDTO> getBatch(@PathVariable Long id) {
        log.debug("REST request to get Batch : {}", id);
        Optional<BatchDTO> batchDTO = batchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(batchDTO);
    }

    /**
     * {@code DELETE  /batches/:id} : delete the "id" batch.
     *
     * @param id the id of the batchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/batches/{id}")
    public ResponseEntity<Void> deleteBatch(@PathVariable Long id) {
        log.debug("REST request to delete Batch : {}", id);
        batchService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
