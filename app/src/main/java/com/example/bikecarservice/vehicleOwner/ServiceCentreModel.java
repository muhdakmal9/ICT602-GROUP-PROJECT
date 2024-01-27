package com.example.bikecarservice.vehicleOwner;

public class ServiceCentreModel {
    String ServiceCentreName, costForActiva, costForBike, costForCar;

    public String getServiceCentreName() {
        return ServiceCentreName;
    }

    public void setServiceCentreName(String serviceCentreName) {
        ServiceCentreName = serviceCentreName;
    }

    public String getCostForActiva() {
        return costForActiva;
    }

    public void setCostForActiva(String costForActiva) {
        this.costForActiva = costForActiva;
    }

    public String getCostForBike() {
        return costForBike;
    }

    public void setCostForBike(String costForBike) {
        this.costForBike = costForBike;
    }

    public String getCostForCar() {
        return costForCar;
    }

    public void setCostForCar(String costForCar) {
        this.costForCar = costForCar;
    }

    public ServiceCentreModel() {
    }

    public ServiceCentreModel(String costForActiva, String costForBike, String costForCar) {
        this.costForActiva = costForActiva;
        this.costForBike = costForBike;
        this.costForCar = costForCar;
    }
}
