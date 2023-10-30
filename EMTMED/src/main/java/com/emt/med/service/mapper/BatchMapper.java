package com.emt.med.service.mapper;

import com.emt.med.domain.Batch;
import com.emt.med.domain.Supply;
import com.emt.med.service.dto.BatchDTO;
import com.emt.med.service.dto.SupplyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Batch} and its DTO {@link BatchDTO}.
 */
@Mapper(componentModel = "spring")
public interface BatchMapper extends EntityMapper<BatchDTO, Batch> {
    @Mapping(target = "supply", source = "supply", qualifiedByName = "supplyName")
    BatchDTO toDto(Batch s);

    @Named("supplyName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SupplyDTO toDtoSupplyName(Supply supply);
}
