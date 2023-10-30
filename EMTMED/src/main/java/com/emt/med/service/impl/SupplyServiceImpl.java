package com.emt.med.service.impl;

import com.emt.med.domain.Supply;
import com.emt.med.repository.SupplyRepository;
import com.emt.med.service.SupplyService;
import com.emt.med.service.dto.SupplyDTO;
import com.emt.med.service.mapper.SupplyMapper;
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
 * Service Implementation for managing {@link Supply}.
 */
@Service
@Transactional
public class SupplyServiceImpl implements SupplyService {

    private final Logger log = LoggerFactory.getLogger(SupplyServiceImpl.class);

    private final SupplyRepository supplyRepository;

    private final SupplyMapper supplyMapper;

    public SupplyServiceImpl(SupplyRepository supplyRepository, SupplyMapper supplyMapper) {
        this.supplyRepository = supplyRepository;
        this.supplyMapper = supplyMapper;
    }

    @Override
    public SupplyDTO save(SupplyDTO supplyDTO) {
        log.debug("Request to save Supply : {}", supplyDTO);
        Supply supply = supplyMapper.toEntity(supplyDTO);
        supply = supplyRepository.save(supply);
        return supplyMapper.toDto(supply);
    }

    @Override
    public SupplyDTO update(SupplyDTO supplyDTO) {
        log.debug("Request to update Supply : {}", supplyDTO);
        Supply supply = supplyMapper.toEntity(supplyDTO);
        supply = supplyRepository.save(supply);
        return supplyMapper.toDto(supply);
    }

    @Override
    public Optional<SupplyDTO> partialUpdate(SupplyDTO supplyDTO) {
        log.debug("Request to partially update Supply : {}", supplyDTO);

        return supplyRepository
            .findById(supplyDTO.getId())
            .map(existingSupply -> {
                supplyMapper.partialUpdate(existingSupply, supplyDTO);

                return existingSupply;
            })
            .map(supplyRepository::save)
            .map(supplyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplyDTO> findAll() {
        log.debug("Request to get all Supplies");
        return supplyRepository.findAll().stream().map(supplyMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<SupplyDTO> findAllWithEagerRelationships(Pageable pageable) {
        return supplyRepository.findAllWithEagerRelationships(pageable).map(supplyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SupplyDTO> findOne(Long id) {
        log.debug("Request to get Supply : {}", id);
        return supplyRepository.findOneWithEagerRelationships(id).map(supplyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Supply : {}", id);
        supplyRepository.deleteById(id);
    }
}
