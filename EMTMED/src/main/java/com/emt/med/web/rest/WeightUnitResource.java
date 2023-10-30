package com.emt.med.web.rest;

import com.emt.med.repository.WeightUnitRepository;
import com.emt.med.service.WeightUnitService;
import com.emt.med.service.dto.WeightUnitDTO;
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
 * REST controller for managing {@link com.emt.med.domain.WeightUnit}.
 */
@RestController
@RequestMapping("/api")
public class WeightUnitResource {

    private final Logger log = LoggerFactory.getLogger(WeightUnitResource.class);

    private static final String ENTITY_NAME = "emtmedWeightUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WeightUnitService weightUnitService;

    private final WeightUnitRepository weightUnitRepository;

    public WeightUnitResource(WeightUnitService weightUnitService, WeightUnitRepository weightUnitRepository) {
        this.weightUnitService = weightUnitService;
        this.weightUnitRepository = weightUnitRepository;
    }

    /**
     * {@code POST  /weight-units} : Create a new weightUnit.
     *
     * @param weightUnitDTO the weightUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new weightUnitDTO, or with status {@code 400 (Bad Request)} if the weightUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/weight-units")
    public ResponseEntity<WeightUnitDTO> createWeightUnit(@RequestBody WeightUnitDTO weightUnitDTO) throws URISyntaxException {
        log.debug("REST request to save WeightUnit : {}", weightUnitDTO);
        if (weightUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new weightUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeightUnitDTO result = weightUnitService.save(weightUnitDTO);
        return ResponseEntity
            .created(new URI("/api/weight-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /weight-units/:id} : Updates an existing weightUnit.
     *
     * @param id the id of the weightUnitDTO to save.
     * @param weightUnitDTO the weightUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weightUnitDTO,
     * or with status {@code 400 (Bad Request)} if the weightUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the weightUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/weight-units/{id}")
    public ResponseEntity<WeightUnitDTO> updateWeightUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WeightUnitDTO weightUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WeightUnit : {}, {}", id, weightUnitDTO);
        if (weightUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weightUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weightUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WeightUnitDTO result = weightUnitService.update(weightUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, weightUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /weight-units/:id} : Partial updates given fields of an existing weightUnit, field will ignore if it is null
     *
     * @param id the id of the weightUnitDTO to save.
     * @param weightUnitDTO the weightUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weightUnitDTO,
     * or with status {@code 400 (Bad Request)} if the weightUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the weightUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the weightUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/weight-units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WeightUnitDTO> partialUpdateWeightUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WeightUnitDTO weightUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WeightUnit partially : {}, {}", id, weightUnitDTO);
        if (weightUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weightUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weightUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WeightUnitDTO> result = weightUnitService.partialUpdate(weightUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, weightUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /weight-units} : get all the weightUnits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weightUnits in body.
     */
    @GetMapping("/weight-units")
    public List<WeightUnitDTO> getAllWeightUnits() {
        log.debug("REST request to get all WeightUnits");
        return weightUnitService.findAll();
    }

    /**
     * {@code GET  /weight-units/:id} : get the "id" weightUnit.
     *
     * @param id the id of the weightUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the weightUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/weight-units/{id}")
    public ResponseEntity<WeightUnitDTO> getWeightUnit(@PathVariable Long id) {
        log.debug("REST request to get WeightUnit : {}", id);
        Optional<WeightUnitDTO> weightUnitDTO = weightUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(weightUnitDTO);
    }

    /**
     * {@code DELETE  /weight-units/:id} : delete the "id" weightUnit.
     *
     * @param id the id of the weightUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/weight-units/{id}")
    public ResponseEntity<Void> deleteWeightUnit(@PathVariable Long id) {
        log.debug("REST request to delete WeightUnit : {}", id);
        weightUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
