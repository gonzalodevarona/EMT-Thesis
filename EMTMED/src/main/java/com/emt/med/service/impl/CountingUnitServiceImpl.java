package com.emt.med.service.impl;

import com.emt.med.domain.CountingUnit;
import com.emt.med.repository.CountingUnitRepository;
import com.emt.med.service.CountingUnitService;
import com.emt.med.service.dto.CountingUnitDTO;
import com.emt.med.service.mapper.CountingUnitMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CountingUnit}.
 */
@Service
@Transactional
public class CountingUnitServiceImpl implements CountingUnitService {

    private final Logger log = LoggerFactory.getLogger(CountingUnitServiceImpl.class);

    private final CountingUnitRepository countingUnitRepository;

    private final CountingUnitMapper countingUnitMapper;

    public CountingUnitServiceImpl(CountingUnitRepository countingUnitRepository, CountingUnitMapper countingUnitMapper) {
        this.countingUnitRepository = countingUnitRepository;
        this.countingUnitMapper = countingUnitMapper;
    }

    @Override
    public CountingUnitDTO save(CountingUnitDTO countingUnitDTO) {
        log.debug("Request to save CountingUnit : {}", countingUnitDTO);
        CountingUnit countingUnit = countingUnitMapper.toEntity(countingUnitDTO);
        countingUnit = countingUnitRepository.save(countingUnit);
        return countingUnitMapper.toDto(countingUnit);
    }

    @Override
    public CountingUnitDTO update(CountingUnitDTO countingUnitDTO) {
        log.debug("Request to update CountingUnit : {}", countingUnitDTO);
        CountingUnit countingUnit = countingUnitMapper.toEntity(countingUnitDTO);
        countingUnit = countingUnitRepository.save(countingUnit);
        return countingUnitMapper.toDto(countingUnit);
    }

    @Override
    public Optional<CountingUnitDTO> partialUpdate(CountingUnitDTO countingUnitDTO) {
        log.debug("Request to partially update CountingUnit : {}", countingUnitDTO);

        return countingUnitRepository
            .findById(countingUnitDTO.getId())
            .map(existingCountingUnit -> {
                countingUnitMapper.partialUpdate(existingCountingUnit, countingUnitDTO);

                return existingCountingUnit;
            })
            .map(countingUnitRepository::save)
            .map(countingUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountingUnitDTO> findAll() {
        log.debug("Request to get all CountingUnits");
        return countingUnitRepository.findAll().stream().map(countingUnitMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CountingUnitDTO> findOne(Long id) {
        log.debug("Request to get CountingUnit : {}", id);
        return countingUnitRepository.findById(id).map(countingUnitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CountingUnit : {}", id);
        countingUnitRepository.deleteById(id);
    }
}
