package com.emt.med.service.impl;

import com.emt.med.domain.Batch;
import com.emt.med.repository.BatchRepository;
import com.emt.med.service.BatchService;
import com.emt.med.service.dto.BatchDTO;
import com.emt.med.service.mapper.BatchMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Batch}.
 */
@Service
@Transactional
public class BatchServiceImpl implements BatchService {

    private final Logger log = LoggerFactory.getLogger(BatchServiceImpl.class);

    private final BatchRepository batchRepository;

    private final BatchMapper batchMapper;

    public BatchServiceImpl(BatchRepository batchRepository, BatchMapper batchMapper) {
        this.batchRepository = batchRepository;
        this.batchMapper = batchMapper;
    }

    @Override
    public BatchDTO save(BatchDTO batchDTO) {
        log.debug("Request to save Batch : {}", batchDTO);
        Batch batch = batchMapper.toEntity(batchDTO);
        batch = batchRepository.save(batch);
        return batchMapper.toDto(batch);
    }

    @Override
    public BatchDTO update(BatchDTO batchDTO) {
        log.debug("Request to update Batch : {}", batchDTO);
        Batch batch = batchMapper.toEntity(batchDTO);
        batch = batchRepository.save(batch);
        return batchMapper.toDto(batch);
    }

    @Override
    public Optional<BatchDTO> partialUpdate(BatchDTO batchDTO) {
        log.debug("Request to partially update Batch : {}", batchDTO);

        return batchRepository
            .findById(batchDTO.getId())
            .map(existingBatch -> {
                batchMapper.partialUpdate(existingBatch, batchDTO);

                return existingBatch;
            })
            .map(batchRepository::save)
            .map(batchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchDTO> findAll() {
        log.debug("Request to get all Batches");
        return batchRepository.findAll().stream().map(batchMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<BatchDTO> findAllWithEagerRelationships(Pageable pageable) {
        return batchRepository.findAllWithEagerRelationships(pageable).map(batchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BatchDTO> findOne(Long id) {
        log.debug("Request to get Batch : {}", id);
        return batchRepository.findOneWithEagerRelationships(id).map(batchMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Batch : {}", id);
        batchRepository.deleteById(id);
    }
}
