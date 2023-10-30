package com.emt.med.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.emt.med.domain.CountingUnit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountingUnitDTO implements Serializable {

    private Long id;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountingUnitDTO)) {
            return false;
        }

        CountingUnitDTO countingUnitDTO = (CountingUnitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, countingUnitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountingUnitDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
