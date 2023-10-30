package com.emt.med.service.mapper;

import com.emt.med.domain.MedicationBatch;
import com.emt.med.service.dto.MedicationBatchDTO;
import org.mapstruct.Mapper;


/**
 * Mapper for the entity {@link com.emt.med.domain.MedicationBatch} and its DTO {@link MedicationBatchDTO}.
 */
@Mapper(componentModel = "spring")
public interface MedicationBatchMapper {

    MedicationBatchDTO toDto(MedicationBatch s);

    MedicationBatch toEntity(MedicationBatchDTO medicationBatchDTO);

}
