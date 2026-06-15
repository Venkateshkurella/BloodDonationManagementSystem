package com.Blood.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "blood_request")
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String patientName;
    private String contactNumber;
    private String email;
    private String bloodGroup;
    private String location;
    private String requiredDate;
    private String status; // "Active", "Fulfilled"

    public BloodRequest() {
        super();
    }

    public BloodRequest(int id, String patientName, String contactNumber, String email, String bloodGroup, String location, String requiredDate, String status) {
        super();
        this.id = id;
        this.patientName = patientName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.requiredDate = requiredDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(String requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BloodRequest [id=" + id + ", patientName=" + patientName + ", contactNumber=" + contactNumber
                + ", email=" + email + ", bloodGroup=" + bloodGroup + ", location=" + location
                + ", requiredDate=" + requiredDate + ", status=" + status + "]";
    }
}
