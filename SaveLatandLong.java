package com.example.a800361.shifterapp;

public class SaveLatandLong {

    double startlatitude,startlongitude,endlatitude,endlongitude,km;
    String picking_location,destination_location;

    public SaveLatandLong() {
    }

    public SaveLatandLong(double startlatitude, double startlongitude, double endlatitude, double endlongitude,
                          double km, String picking_location, String destination_location) {
        this.startlatitude = startlatitude;
        this.startlongitude = startlongitude;
        this.endlatitude = endlatitude;
        this.endlongitude = endlongitude;
        this.km = km;
        this.picking_location = picking_location;
        this.destination_location = destination_location;
    }

    public double getStartlatitude() {
        return startlatitude;
    }

    public void setStartlatitude(double startlatitude) {
        this.startlatitude = startlatitude;
    }

    public double getStartlongitude() {
        return startlongitude;
    }

    public void setStartlongitude(double startlongitude) {
        this.startlongitude = startlongitude;
    }

    public double getEndlatitude() {
        return endlatitude;
    }

    public void setEndlatitude(double endlatitude) {
        this.endlatitude = endlatitude;
    }

    public double getEndlongitude() {
        return endlongitude;
    }

    public void setEndlongitude(double endlongitude) {
        this.endlongitude = endlongitude;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public String getPicking_location() {
        return picking_location;
    }

    public void setPicking_location(String picking_location) {
        this.picking_location = picking_location;
    }

    public String getDestination_location() {
        return destination_location;
    }

    public void setDestination_location(String destination_location) {
        this.destination_location = destination_location;
    }
}
