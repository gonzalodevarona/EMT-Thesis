package com.emt.med.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Field.
 */
@Entity
@Table(name = "field")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "fields")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "batches", "fields", "location", "order", "weightUnit", "countingUnit" }, allowSetters = true)
    private Set<Supply> supplies = new HashSet<>();

    @ManyToMany(mappedBy = "fields")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "supplies", "fields" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Field id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public Field value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Field name(String name) {
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
            this.supplies.forEach(i -> i.removeField(this));
        }
        if (supplies != null) {
            supplies.forEach(i -> i.addField(this));
        }
        this.supplies = supplies;
    }

    public Field supplies(Set<Supply> supplies) {
        this.setSupplies(supplies);
        return this;
    }

    public Field addSupply(Supply supply) {
        this.supplies.add(supply);
        supply.getFields().add(this);
        return this;
    }

    public Field removeSupply(Supply supply) {
        this.supplies.remove(supply);
        supply.getFields().remove(this);
        return this;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.removeField(this));
        }
        if (orders != null) {
            orders.forEach(i -> i.addField(this));
        }
        this.orders = orders;
    }

    public Field orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public Field addOrder(Order order) {
        this.orders.add(order);
        order.getFields().add(this);
        return this;
    }

    public Field removeOrder(Order order) {
        this.orders.remove(order);
        order.getFields().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Field)) {
            return false;
        }
        return id != null && id.equals(((Field) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Field{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
