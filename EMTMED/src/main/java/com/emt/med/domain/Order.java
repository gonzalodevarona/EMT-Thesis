package com.emt.med.domain;

import com.emt.med.domain.enumeration.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "practitioner")
    private Integer practitioner;

    @Column(name = "authored_on")
    private LocalDate authoredOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "batches", "fields", "location", "order", "weightUnit", "countingUnit" }, allowSetters = true)
    private Set<Supply> supplies = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_jhi_order__field",
        joinColumns = @JoinColumn(name = "jhi_order_id"),
        inverseJoinColumns = @JoinColumn(name = "field_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "supplies", "orders" }, allowSetters = true)
    private Set<Field> fields = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPractitioner() {
        return this.practitioner;
    }

    public Order practitioner(Integer practitioner) {
        this.setPractitioner(practitioner);
        return this;
    }

    public void setPractitioner(Integer practitioner) {
        this.practitioner = practitioner;
    }

    public LocalDate getAuthoredOn() {
        return this.authoredOn;
    }

    public Order authoredOn(LocalDate authoredOn) {
        this.setAuthoredOn(authoredOn);
        return this;
    }

    public void setAuthoredOn(LocalDate authoredOn) {
        this.authoredOn = authoredOn;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public Order status(OrderStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<Supply> getSupplies() {
        return this.supplies;
    }

    public void setSupplies(Set<Supply> supplies) {
        if (this.supplies != null) {
            this.supplies.forEach(i -> i.setOrder(null));
        }
        if (supplies != null) {
            supplies.forEach(i -> i.setOrder(this));
        }
        this.supplies = supplies;
    }

    public Order supplies(Set<Supply> supplies) {
        this.setSupplies(supplies);
        return this;
    }

    public Order addSupply(Supply supply) {
        this.supplies.add(supply);
        supply.setOrder(this);
        return this;
    }

    public Order removeSupply(Supply supply) {
        this.supplies.remove(supply);
        supply.setOrder(null);
        return this;
    }

    public Set<Field> getFields() {
        return this.fields;
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
    }

    public Order fields(Set<Field> fields) {
        this.setFields(fields);
        return this;
    }

    public Order addField(Field field) {
        this.fields.add(field);
        field.getOrders().add(this);
        return this;
    }

    public Order removeField(Field field) {
        this.fields.remove(field);
        field.getOrders().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", practitioner=" + getPractitioner() +
            ", authoredOn='" + getAuthoredOn() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
