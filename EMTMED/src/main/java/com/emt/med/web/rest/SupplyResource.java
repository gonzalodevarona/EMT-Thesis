package com.emt.med.web.rest;

import com.emt.med.repository.SupplyRepository;
import com.emt.med.service.SupplyService;
import com.emt.med.service.dto.SupplyDTO;
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
 * REST controller for managing {@link com.emt.med.domain.Supply}.
 */
@RestController
@RequestMapping("/api")
public class SupplyResource {

    private final Logger log = LoggerFactory.getLogger(SupplyResource.class);

    private static final String ENTITY_NAME = "emtmedSupply";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupplyService supplyService;

    private final SupplyRepository supplyRepository;

    public SupplyResource(SupplyService supplyService, SupplyRepository supplyRepository) {
        this.supplyService = supplyService;
        this.supplyRepository = supplyRepository;
    }

    /**
     * {@code POST  /supplies} : Create a new supply.
     *
     * @param supplyDTO the supplyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplyDTO, or with status {@code 400 (Bad Request)} if the supply has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supplies")
    public ResponseEntity<SupplyDTO> createSupply(@RequestBody SupplyDTO supplyDTO) throws URISyntaxException {
        log.debug("REST request to save Supply : {}", supplyDTO);
        if (supplyDTO.getId() != null) {
            throw new BadRequestAlertException("A new supply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyDTO result = supplyService.save(supplyDTO);
        return ResponseEntity
            .created(new URI("/api/supplies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supplies/:id} : Updates an existing supply.
     *
     * @param id the id of the supplyDTO to save.
     * @param supplyDTO the supplyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplyDTO,
     * or with status {@code 400 (Bad Request)} if the supplyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supplyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supplies/{id}")
    public ResponseEntity<SupplyDTO> updateSupply(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SupplyDTO supplyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Supply : {}, {}", id, supplyDTO);
        if (supplyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supplyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SupplyDTO result = supplyService.update(supplyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /supplies/:id} : Partial updates given fields of an existing supply, field will ignore if it is null
     *
     * @param id the id of the supplyDTO to save.
     * @param supplyDTO the supplyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplyDTO,
     * or with status {@code 400 (Bad Request)} if the supplyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the supplyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the supplyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/supplies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SupplyDTO> partialUpdateSupply(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SupplyDTO supplyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Supply partially : {}, {}", id, supplyDTO);
        if (supplyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supplyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SupplyDTO> result = supplyService.partialUpdate(supplyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /supplies} : get all the supplies.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplies in body.
     */
    @GetMapping("/supplies")
    public List<SupplyDTO> getAllSupplies(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Supplies");
        return supplyService.findAll();
    }

    /**
     * {@code GET  /supplies/:id} : get the "id" supply.
     *
     * @param id the id of the supplyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supplies/{id}")
    public ResponseEntity<SupplyDTO> getSupply(@PathVariable Long id) {
        log.debug("REST request to get Supply : {}", id);
        Optional<SupplyDTO> supplyDTO = supplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyDTO);
    }

    /**
     * {@code DELETE  /supplies/:id} : delete the "id" supply.
     *
     * @param id the id of the supplyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supplies/{id}")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        log.debug("REST request to delete Supply : {}", id);
        supplyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
