package com.emt.med.service.mapper;

import com.emt.med.domain.CountingUnit;
import com.emt.med.domain.Field;
import com.emt.med.domain.Location;
import com.emt.med.domain.Order;
import com.emt.med.domain.Supply;
import com.emt.med.domain.WeightUnit;
import com.emt.med.service.dto.CountingUnitDTO;
import com.emt.med.service.dto.FieldDTO;
import com.emt.med.service.dto.LocationDTO;
import com.emt.med.service.dto.OrderDTO;
import com.emt.med.service.dto.SupplyDTO;
import com.emt.med.service.dto.WeightUnitDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Supply} and its DTO {@link SupplyDTO}.
 */
@Mapper(componentModel = "spring")
public interface SupplyMapper extends EntityMapper<SupplyDTO, Supply> {
    @Mapping(target = "fields", source = "fields", qualifiedByName = "fieldNameSet")
    @Mapping(target = "location", source = "location", qualifiedByName = "locationName")
    @Mapping(target = "order", source = "order", qualifiedByName = "orderId")
    @Mapping(target = "weightUnit", source = "weightUnit", qualifiedByName = "weightUnitName")
    @Mapping(target = "countingUnit", source = "countingUnit", qualifiedByName = "countingUnitName")
    SupplyDTO toDto(Supply s);

    @Mapping(target = "removeField", ignore = true)
    Supply toEntity(SupplyDTO supplyDTO);

    @Named("fieldName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    FieldDTO toDtoFieldName(Field field);

    @Named("fieldNameSet")
    default Set<FieldDTO> toDtoFieldNameSet(Set<Field> field) {
        return field.stream().map(this::toDtoFieldName).collect(Collectors.toSet());
    }

    @Named("locationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    LocationDTO toDtoLocationName(Location location);

    @Named("orderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDTO toDtoOrderId(Order order);

    @Named("weightUnitName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    WeightUnitDTO toDtoWeightUnitName(WeightUnit weightUnit);

    @Named("countingUnitName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CountingUnitDTO toDtoCountingUnitName(CountingUnit countingUnit);
}
