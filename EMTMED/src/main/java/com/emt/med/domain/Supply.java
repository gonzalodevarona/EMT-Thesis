package com.emt.med.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Supply.
 */
@Entity
@Table(name = "supply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Supply implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "quantity")
    private Integer quantity;

    @OneToMany(mappedBy = "supply")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "supply" }, allowSetters = true)
    private Set<Batch> batches = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_supply__field",
        joinColumns = @JoinColumn(name = "supply_id"),
        inverseJoinColumns = @JoinColumn(name = "field_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "supplies", "orders" }, allowSetters = true)
    private Set<Field> fields = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "supplies" }, allowSetters = true)
    private Location location;

    @ManyToOne
    @JsonIgnoreProperties(value = { "supplies", "fields" }, allowSetters = true)
    private Order order;

    @ManyToOne
    @JsonIgnoreProperties(value = { "supplies" }, allowSetters = true)
    private WeightUnit weightUnit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "supplies" }, allowSetters = true)
    private CountingUnit countingUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Supply id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Supply name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Supply weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Supply quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Set<Batch> getBatches() {
        return this.batches;
    }

    public void setBatches(Set<Batch> batches) {
        if (this.batches != null) {
            this.batches.forEach(i -> i.setSupply(null));
        }
        if (batches != null) {
            batches.forEach(i -> i.setSupply(this));
        }
        this.batches = batches;
    }

    public Supply batches(Set<Batch> batches) {
        this.setBatches(batches);
        return this;
    }

    public Supply addBatch(Batch batch) {
        this.batches.add(batch);
        batch.setSupply(this);
        return this;
    }

    public Supply removeBatch(Batch batch) {
        this.batches.remove(batch);
        batch.setSupply(null);
        return this;
    }

    public Set<Field> getFields() {
        return this.fields;
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
    }

    public Supply fields(Set<Field> fields) {
        this.setFields(fields);
        return this;
    }

    public Supply addField(Field field) {
        this.fields.add(field);
        field.getSupplies().add(this);
        return this;
    }

    public Supply removeField(Field field) {
        this.fields.remove(field);
        field.getSupplies().remove(this);
        return this;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Supply location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Supply order(Order order) {
        this.setOrder(order);
        return this;
    }

    public WeightUnit getWeightUnit() {
        return this.weightUnit;
    }

    public void setWeightUnit(WeightUnit weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Supply weightUnit(WeightUnit weightUnit) {
        this.setWeightUnit(weightUnit);
        return this;
    }

    public CountingUnit getCountingUnit() {
        return this.countingUnit;
    }

    public void setCountingUnit(CountingUnit countingUnit) {
        this.countingUnit = countingUnit;
    }

    public Supply countingUnit(CountingUnit countingUnit) {
        this.setCountingUnit(countingUnit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Supply)) {
            return false;
        }
        return id != null && id.equals(((Supply) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Supply{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", weight=" + getWeight() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
