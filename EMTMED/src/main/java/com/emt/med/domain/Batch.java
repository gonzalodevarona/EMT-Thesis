package com.emt.med.domain;

import com.emt.med.domain.enumeration.BatchStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Batch.
 */
@Entity
@Table(name = "batch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Batch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "administration_route")
    private String administrationRoute;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BatchStatus status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "batches", "fields", "location", "order", "weightUnit", "countingUnit" }, allowSetters = true)
    private Supply supply;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Batch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public Batch manufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getAdministrationRoute() {
        return this.administrationRoute;
    }

    public Batch administrationRoute(String administrationRoute) {
        this.setAdministrationRoute(administrationRoute);
        return this;
    }

    public void setAdministrationRoute(String administrationRoute) {
        this.administrationRoute = administrationRoute;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    public Batch expirationDate(LocalDate expirationDate) {
        this.setExpirationDate(expirationDate);
        return this;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BatchStatus getStatus() {
        return this.status;
    }

    public Batch status(BatchStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(BatchStatus status) {
        this.status = status;
    }

    public Supply getSupply() {
        return this.supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public Batch supply(Supply supply) {
        this.setSupply(supply);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Batch)) {
            return false;
        }
        return id != null && id.equals(((Batch) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Batch{" +
            "id=" + getId() +
            ", manufacturer='" + getManufacturer() + "'" +
            ", administrationRoute='" + getAdministrationRoute() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
