package com.emt.med.domain;

import javax.persistence.Entity;

@Entity
public class MedicationBatch extends Batch {

    private int quantity;
    private String cum;

    public MedicationBatch() {
    }

    public MedicationBatch(int quantity, String cum) {
        this.quantity = quantity;
        this.cum = cum;
    }

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
}
