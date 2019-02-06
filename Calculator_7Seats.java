package com.example.a800361.shifterapp;

public class Calculator_7Seats {

    double initial = 20.1;
    double Price_per_mile = 15.3;
    double Price_per_minute= 1.64;
    double Service_cost = 20;

    public Calculator_7Seats() {
    }

    public Calculator_7Seats(double initial, double price_per_mile, double price_per_minute, double service_cost) {
        this.initial = initial;
        Price_per_mile = price_per_mile;
        Price_per_minute = price_per_minute;
        Service_cost = service_cost;
    }

    public double getInitial() {
        return initial;
    }

    public void setInitial(double initial) {
        this.initial = initial;
    }

    public double getPrice_per_mile() {
        return Price_per_mile;
    }

    public void setPrice_per_mile(double price_per_mile) {
        Price_per_mile = price_per_mile;
    }

    public double getPrice_per_minute() {
        return Price_per_minute;
    }

    public void setPrice_per_minute(double price_per_minute) {
        Price_per_minute = price_per_minute;
    }

    public double getService_cost() {
        return Service_cost;
    }

    public void setService_cost(double service_cost) {
        Service_cost = service_cost;
    }
}
