package com.example.tappinginthemap.model;

import java.util.List;

public class CoronaInfo {
    private Patient patient;
    private java.util.List<Location> location = null;


    public CoronaInfo(Patient patient, List<Location> location) {
        this.patient = patient;
        this.location = location;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Location> getLocation() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }
}
