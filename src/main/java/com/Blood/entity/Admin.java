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
    private String location;
    private String username;
    private String password;

    public Admin() {
        super();
    }

    public Admin(int id, String bloodCenter, String location, String username, String password) {
        super();
        this.id = id;
        this.bloodCenter = bloodCenter;
        this.location = location;
        this.username = username;
        this.password = password;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin [id=" + id + ", bloodCenter=" + bloodCenter + ", location=" + location + ", username=" + username + "]";
    }
}