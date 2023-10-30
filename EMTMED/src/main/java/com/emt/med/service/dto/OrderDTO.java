package com.emt.med.service.dto;

import com.emt.med.domain.enumeration.OrderStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.emt.med.domain.Order} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDTO implements Serializable {

    private Long id;

    private Integer practitioner;

    private LocalDate authoredOn;

    private OrderStatus status;

    private Set<FieldDTO> fields = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(Integer practitioner) {
        this.practitioner = practitioner;
    }

    public LocalDate getAuthoredOn() {
        return authoredOn;
    }

    public void setAuthoredOn(LocalDate authoredOn) {
        this.authoredOn = authoredOn;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<FieldDTO> getFields() {
        return fields;
    }

    public void setFields(Set<FieldDTO> fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", practitioner=" + getPractitioner() +
            ", authoredOn='" + getAuthoredOn() + "'" +
            ", status='" + getStatus() + "'" +
            ", fields=" + getFields() +
            "}";
    }
}
