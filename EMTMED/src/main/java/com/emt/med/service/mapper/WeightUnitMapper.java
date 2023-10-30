package com.emt.med.service.mapper;

import com.emt.med.domain.WeightUnit;
import com.emt.med.service.dto.WeightUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WeightUnit} and its DTO {@link WeightUnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface WeightUnitMapper extends EntityMapper<WeightUnitDTO, WeightUnit> {}
