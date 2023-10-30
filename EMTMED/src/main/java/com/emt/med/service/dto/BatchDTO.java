package com.emt.med.service.dto;

import com.emt.med.domain.enumeration.BatchStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.emt.med.domain.Batch} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BatchDTO implements Serializable {

    private Long id;

    private String manufacturer;

    private String administrationRoute;

    private LocalDate expirationDate;

    private BatchStatus status;

    private SupplyDTO supply;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getAdministrationRoute() {
        return administrationRoute;
    }

    public void setAdministrationRoute(String administrationRoute) {
        this.administrationRoute = administrationRoute;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BatchStatus getStatus() {
        return status;
    }

    public void setStatus(BatchStatus status) {
        this.status = status;
    }

    public SupplyDTO getSupply() {
        return supply;
    }

    public void setSupply(SupplyDTO supply) {
        this.supply = supply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatchDTO)) {
            return false;
        }

        BatchDTO batchDTO = (BatchDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, batchDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BatchDTO{" +
            "id=" + getId() +
            ", manufacturer='" + getManufacturer() + "'" +
            ", administrationRoute='" + getAdministrationRoute() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", supply=" + getSupply() +
            "}";
    }
}
