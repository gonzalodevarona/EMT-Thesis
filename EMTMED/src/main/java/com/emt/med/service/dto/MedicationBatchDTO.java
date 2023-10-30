package com.emt.med.service.dto;

import com.emt.med.domain.Supply;
import com.emt.med.domain.enumeration.BatchStatus;
import com.emt.med.domain.enumeration.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.emt.med.domain.Order} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MedicationBatchDTO extends BatchDTO{
    private int quantity;
    private String cum;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCum() {
        return cum;
    }

    public void setCum(String cum) {
        this.cum = cum;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicationBatchDTO)) {
            return false;
        }

        MedicationBatchDTO medicationBatchDTO = (MedicationBatchDTO) o;
        if (super.getId() == null) {
            return false;
        }
        return Objects.equals(super.getId(), medicationBatchDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }

    @Override
    public String toString() {
        return super.toString()+"MedicationBatchDTO{" +
            "quantity=" + quantity +
            ", cum='" + cum + '\'' +
            '}';
    }


}
