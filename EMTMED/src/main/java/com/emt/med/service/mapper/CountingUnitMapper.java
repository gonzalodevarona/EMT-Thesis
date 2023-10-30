package com.emt.med.service.mapper;

import com.emt.med.domain.CountingUnit;
import com.emt.med.service.dto.CountingUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CountingUnit} and its DTO {@link CountingUnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface CountingUnitMapper extends EntityMapper<CountingUnitDTO, CountingUnit> {}
