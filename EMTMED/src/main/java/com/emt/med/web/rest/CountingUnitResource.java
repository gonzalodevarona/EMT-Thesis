package com.emt.med.web.rest;

import com.emt.med.repository.CountingUnitRepository;
import com.emt.med.service.CountingUnitService;
import com.emt.med.service.dto.CountingUnitDTO;
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
 * REST controller for managing {@link com.emt.med.domain.CountingUnit}.
 */
@RestController
@RequestMapping("/api")
public class CountingUnitResource {

    private final Logger log = LoggerFactory.getLogger(CountingUnitResource.class);

    private static final String ENTITY_NAME = "emtmedCountingUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountingUnitService countingUnitService;

    private final CountingUnitRepository countingUnitRepository;

    public CountingUnitResource(CountingUnitService countingUnitService, CountingUnitRepository countingUnitRepository) {
        this.countingUnitService = countingUnitService;
        this.countingUnitRepository = countingUnitRepository;
    }

    /**
     * {@code POST  /counting-units} : Create a new countingUnit.
     *
     * @param countingUnitDTO the countingUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countingUnitDTO, or with status {@code 400 (Bad Request)} if the countingUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/counting-units")
    public ResponseEntity<CountingUnitDTO> createCountingUnit(@RequestBody CountingUnitDTO countingUnitDTO) throws URISyntaxException {
        log.debug("REST request to save CountingUnit : {}", countingUnitDTO);
        if (countingUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new countingUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CountingUnitDTO result = countingUnitService.save(countingUnitDTO);
        return ResponseEntity
            .created(new URI("/api/counting-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /counting-units/:id} : Updates an existing countingUnit.
     *
     * @param id the id of the countingUnitDTO to save.
     * @param countingUnitDTO the countingUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countingUnitDTO,
     * or with status {@code 400 (Bad Request)} if the countingUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countingUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/counting-units/{id}")
    public ResponseEntity<CountingUnitDTO> updateCountingUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CountingUnitDTO countingUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CountingUnit : {}, {}", id, countingUnitDTO);
        if (countingUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, countingUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!countingUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CountingUnitDTO result = countingUnitService.update(countingUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countingUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /counting-units/:id} : Partial updates given fields of an existing countingUnit, field will ignore if it is null
     *
     * @param id the id of the countingUnitDTO to save.
     * @param countingUnitDTO the countingUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countingUnitDTO,
     * or with status {@code 400 (Bad Request)} if the countingUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the countingUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the countingUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/counting-units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CountingUnitDTO> partialUpdateCountingUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CountingUnitDTO countingUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CountingUnit partially : {}, {}", id, countingUnitDTO);
        if (countingUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, countingUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!countingUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CountingUnitDTO> result = countingUnitService.partialUpdate(countingUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countingUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /counting-units} : get all the countingUnits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countingUnits in body.
     */
    @GetMapping("/counting-units")
    public List<CountingUnitDTO> getAllCountingUnits() {
        log.debug("REST request to get all CountingUnits");
        return countingUnitService.findAll();
    }

    /**
     * {@code GET  /counting-units/:id} : get the "id" countingUnit.
     *
     * @param id the id of the countingUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countingUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/counting-units/{id}")
    public ResponseEntity<CountingUnitDTO> getCountingUnit(@PathVariable Long id) {
        log.debug("REST request to get CountingUnit : {}", id);
        Optional<CountingUnitDTO> countingUnitDTO = countingUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(countingUnitDTO);
    }

    /**
     * {@code DELETE  /counting-units/:id} : delete the "id" countingUnit.
     *
     * @param id the id of the countingUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/counting-units/{id}")
    public ResponseEntity<Void> deleteCountingUnit(@PathVariable Long id) {
        log.debug("REST request to delete CountingUnit : {}", id);
        countingUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
