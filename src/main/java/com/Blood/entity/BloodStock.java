package com.Blood.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "blood_stock")
public class BloodStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "admin_id") // Links to the Admin (blood center account)
    private Admin bloodCenter;

    private String bloodGroup;
    private int quantity;

    public BloodStock() {
        super();
    }

    public BloodStock(int id, Admin bloodCenter, String bloodGroup, int quantity) {
        super();
        this.id = id;
        this.bloodCenter = bloodCenter;
        this.bloodGroup = bloodGroup;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Admin getBloodCenter() {
        return bloodCenter;
    }

    public void setBloodCenter(Admin bloodCenter) {
        this.bloodCenter = bloodCenter;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BloodStock [id=" + id + ", bloodCenter=" + (bloodCenter != null ? bloodCenter.getBloodCenter() : "null")
                + ", bloodGroup=" + bloodGroup + ", quantity=" + quantity + "]";
    }
}
