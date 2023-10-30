package com.emt.med.service.mapper;

import com.emt.med.domain.Field;
import com.emt.med.domain.Order;
import com.emt.med.service.dto.FieldDTO;
import com.emt.med.service.dto.OrderDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Mapping(target = "fields", source = "fields", qualifiedByName = "fieldIdSet")
    OrderDTO toDto(Order s);

    @Mapping(target = "removeField", ignore = true)
    Order toEntity(OrderDTO orderDTO);

    @Named("fieldId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldDTO toDtoFieldId(Field field);

    @Named("fieldIdSet")
    default Set<FieldDTO> toDtoFieldIdSet(Set<Field> field) {
        return field.stream().map(this::toDtoFieldId).collect(Collectors.toSet());
    }
}
