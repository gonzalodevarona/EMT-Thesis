package com.emt.med.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CountingUnit.
 */
@Entity
@Table(name = "counting_unit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountingUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "countingUnit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "batches", "fields", "location", "order", "weightUnit", "countingUnit" }, allowSetters = true)
    private Set<Supply> supplies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CountingUnit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CountingUnit name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Supply> getSupplies() {
        return this.supplies;
    }

    public void setSupplies(Set<Supply> supplies) {
        if (this.supplies != null) {
            this.supplies.forEach(i -> i.setCountingUnit(null));
        }
        if (supplies != null) {
            supplies.forEach(i -> i.setCountingUnit(this));
        }
        this.supplies = supplies;
    }

    public CountingUnit supplies(Set<Supply> supplies) {
        this.setSupplies(supplies);
        return this;
    }

    public CountingUnit addSupply(Supply supply) {
        this.supplies.add(supply);
        supply.setCountingUnit(this);
        return this;
    }

    public CountingUnit removeSupply(Supply supply) {
        this.supplies.remove(supply);
        supply.setCountingUnit(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountingUnit)) {
            return false;
        }
        return id != null && id.equals(((CountingUnit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountingUnit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
