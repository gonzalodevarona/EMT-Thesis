package com.emt.med.service.impl;

import com.emt.med.domain.WeightUnit;
import com.emt.med.repository.WeightUnitRepository;
import com.emt.med.service.WeightUnitService;
import com.emt.med.service.dto.WeightUnitDTO;
import com.emt.med.service.mapper.WeightUnitMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WeightUnit}.
 */
@Service
@Transactional
public class WeightUnitServiceImpl implements WeightUnitService {

    private final Logger log = LoggerFactory.getLogger(WeightUnitServiceImpl.class);

    private final WeightUnitRepository weightUnitRepository;

    private final WeightUnitMapper weightUnitMapper;

    public WeightUnitServiceImpl(WeightUnitRepository weightUnitRepository, WeightUnitMapper weightUnitMapper) {
        this.weightUnitRepository = weightUnitRepository;
        this.weightUnitMapper = weightUnitMapper;
    }

    @Override
    public WeightUnitDTO save(WeightUnitDTO weightUnitDTO) {
        log.debug("Request to save WeightUnit : {}", weightUnitDTO);
        WeightUnit weightUnit = weightUnitMapper.toEntity(weightUnitDTO);
        weightUnit = weightUnitRepository.save(weightUnit);
        return weightUnitMapper.toDto(weightUnit);
    }

    @Override
    public WeightUnitDTO update(WeightUnitDTO weightUnitDTO) {
        log.debug("Request to update WeightUnit : {}", weightUnitDTO);
        WeightUnit weightUnit = weightUnitMapper.toEntity(weightUnitDTO);
        weightUnit = weightUnitRepository.save(weightUnit);
        return weightUnitMapper.toDto(weightUnit);
    }

    @Override
    public Optional<WeightUnitDTO> partialUpdate(WeightUnitDTO weightUnitDTO) {
        log.debug("Request to partially update WeightUnit : {}", weightUnitDTO);

        return weightUnitRepository
            .findById(weightUnitDTO.getId())
            .map(existingWeightUnit -> {
                weightUnitMapper.partialUpdate(existingWeightUnit, weightUnitDTO);

                return existingWeightUnit;
            })
            .map(weightUnitRepository::save)
            .map(weightUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeightUnitDTO> findAll() {
        log.debug("Request to get all WeightUnits");
        return weightUnitRepository.findAll().stream().map(weightUnitMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WeightUnitDTO> findOne(Long id) {
        log.debug("Request to get WeightUnit : {}", id);
        return weightUnitRepository.findById(id).map(weightUnitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WeightUnit : {}", id);
        weightUnitRepository.deleteById(id);
    }
}
