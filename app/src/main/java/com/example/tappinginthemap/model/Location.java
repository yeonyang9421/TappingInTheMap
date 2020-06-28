
package com.example.tappinginthemap.model;


import java.util.Date;

public class Location {

    private String id;
    private int idLoc;
    private String location;
    private Date movingTime;
    private double latitude;
    private double longtitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdLoc() {
        return idLoc;
    }

    public void setIdLoc(int idLoc) {
        this.idLoc = idLoc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getMovingTime() {
        return movingTime;
    }

    public void setMovingTime(Date movingTime) {
        this.movingTime = movingTime;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLon() {
        return longtitude;
    }

    public void setLon(double lon) {
        this.longtitude = lon;
    }

    @Override
    public String toString() {
        return "Corona{" +
                "id='" + id + '\'' +
                ", idLoc=" + idLoc +
                ", location='" + location + '\'' +
                ", movingTime=" + movingTime +
                ", latitude=" + latitude +
                ", longtitude=" + longtitude +
                '}';
    }
}
