
package com.example.tappinginthemap.model;


import java.util.Date;

public class Patient {

    private String id;
    private String infectionRoute;
    private Date confirmedDate;
    private String residence;
    private String isolationFacility;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfectionRoute() {
        return infectionRoute;
    }

    public void setInfectionRoute(String infectionRoute) {
        this.infectionRoute = infectionRoute;
    }

    public Date getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getIsolationFacility() {
        return isolationFacility;
    }

    public void setIsolationFacility(String isolationFacility) {
        this.isolationFacility = isolationFacility;
    }
}
