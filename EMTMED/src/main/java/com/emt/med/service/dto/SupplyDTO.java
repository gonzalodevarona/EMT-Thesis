package com.emt.med.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.emt.med.domain.Supply} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SupplyDTO implements Serializable {

    private Long id;

    private String name;

    private Double weight;

    private Integer quantity;

    private Set<FieldDTO> fields = new HashSet<>();

    private LocationDTO location;

    private OrderDTO order;

    private WeightUnitDTO weightUnit;

    private CountingUnitDTO countingUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Set<FieldDTO> getFields() {
        return fields;
    }

    public void setFields(Set<FieldDTO> fields) {
        this.fields = fields;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public WeightUnitDTO getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(WeightUnitDTO weightUnit) {
        this.weightUnit = weightUnit;
    }

    public CountingUnitDTO getCountingUnit() {
        return countingUnit;
    }

    public void setCountingUnit(CountingUnitDTO countingUnit) {
        this.countingUnit = countingUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupplyDTO)) {
            return false;
        }

        SupplyDTO supplyDTO = (SupplyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, supplyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", weight=" + getWeight() +
            ", quantity=" + getQuantity() +
            ", fields=" + getFields() +
            ", location=" + getLocation() +
            ", order=" + getOrder() +
            ", weightUnit=" + getWeightUnit() +
            ", countingUnit=" + getCountingUnit() +
            "}";
    }
}
