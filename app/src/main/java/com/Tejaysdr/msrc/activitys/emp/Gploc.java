package com.Tejaysdr.msrc.activitys.emp;

public class Gploc {
    private String id;
    private String gps_latitude;
    private String gps_longitude;
    private String address;
    private String datecaptured;

    public Gploc() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGps_latitude() {
        return gps_latitude;
    }

    public void setGps_latitude(String gps_latitude) {
        this.gps_latitude = gps_latitude;
    }

    public String getGps_longitude() {
        return gps_longitude;
    }

    public void setGps_longitude(String gps_longitude) {
        this.gps_longitude = gps_longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDatecaptured() {
        return datecaptured;
    }

    public void setDatecaptured(String datecaptured) {
        this.datecaptured = datecaptured;
    }

    public Gploc(String id, String gps_latitude, String gps_longitude, String address, String datecaptured) {
        this.id = id;
        this.gps_latitude = gps_latitude;
        this.gps_longitude = gps_longitude;
        this.address = address;
        this.datecaptured = datecaptured;
    }
}
