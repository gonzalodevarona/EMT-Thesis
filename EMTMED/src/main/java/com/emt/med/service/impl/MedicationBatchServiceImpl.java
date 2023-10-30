package com.emt.med.service.impl;

import com.emt.med.domain.MedicationBatch;
import com.emt.med.repository.MedicationBatchRepository;
import com.emt.med.service.MedicationBatchService;
import com.emt.med.service.dto.MedicationBatchDTO;
import com.emt.med.service.mapper.MedicationBatchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicationBatchServiceImpl implements MedicationBatchService {

    private final Logger log = LoggerFactory.getLogger(MedicationBatchServiceImpl.class);

    private MedicationBatchRepository medicationBatchRepository;

    private MedicationBatchMapper medicationBatchMapper;

    public MedicationBatchServiceImpl(MedicationBatchRepository medicationBatchRepository, MedicationBatchMapper medicationBatchMapper) {
        this.medicationBatchRepository = medicationBatchRepository;
        this.medicationBatchMapper = medicationBatchMapper;
    }

    @Override
    public MedicationBatchDTO save(MedicationBatchDTO medicationBatchDTO) {
        log.debug("Request to save MedicationBatch : {}", medicationBatchDTO);
        MedicationBatch medicationBatch = medicationBatchMapper.toEntity(medicationBatchDTO);
        medicationBatch = medicationBatchRepository.save(medicationBatch);
        return medicationBatchMapper.toDto(medicationBatch);
    }

    @Override
    public MedicationBatchDTO update(MedicationBatchDTO medicationBatchDTO) {
        log.debug("Request to update MedicationBatch : {}", medicationBatchDTO);
        MedicationBatch medicationBatch = medicationBatchMapper.toEntity(medicationBatchDTO);
        medicationBatch = medicationBatchRepository.save(medicationBatch);
        return medicationBatchMapper.toDto(medicationBatch);
    }

    @Override
    public Optional<MedicationBatchDTO> partialUpdate(MedicationBatchDTO medicationBatchDTO) {
        log.debug("Request to partially update MedicationBatch : {}", medicationBatchDTO);

        return medicationBatchRepository
            .findById(medicationBatchDTO.getId())
            .map(existingMedicationBatch -> {
                medicationBatchMapper.toDto(existingMedicationBatch);

                return existingMedicationBatch;
            })
            .map(medicationBatchRepository::save)
            .map(medicationBatchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicationBatchDTO> findAll() {
        log.debug("Request to get all MedicationBatchs");
        return medicationBatchRepository.findAll().stream().map(medicationBatchMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MedicationBatchDTO> findOne(Long id) {
        log.debug("Request to get MedicationBatch : {}", id);
        return medicationBatchRepository.findById(id).map(medicationBatchMapper::toDto);

    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicationBatch : {}", id);
        medicationBatchRepository.deleteById(id);
    }
}
