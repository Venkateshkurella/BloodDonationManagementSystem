package com.Blood.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bloodCenter;
    private String bloodGroup;
    private String location;

    // No-Argument Constructor
    public Admin() {
        super();
    }

    // Parameterized Constructor
    public Admin(int id, String bloodCenter, String bloodGroup, String location) {
        super();
        this.id = id;
        this.bloodCenter = bloodCenter;
        this.bloodGroup = bloodGroup;
        this.location = location;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBloodCenter() {
        return bloodCenter;
    }

    public void setBloodCenter(String bloodCenter) {
        this.bloodCenter = bloodCenter;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Admin [id=" + id +
                ", bloodCenter=" + bloodCenter +
                ", bloodGroup=" + bloodGroup +
                ", location=" + location + "]";
    }
}