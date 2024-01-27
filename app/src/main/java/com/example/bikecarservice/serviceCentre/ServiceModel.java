package com.example.bikecarservice.serviceCentre;

public class ServiceModel {
    String username, vehicleType, cost, vehicleNumber;

    public ServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public ServiceModel(String username, String vehicleType, String cost, String vehicleNumber) {
        this.username = username;
        this.vehicleType = vehicleType;
        this.cost = cost;
        this.vehicleNumber = vehicleNumber;
    }
}
