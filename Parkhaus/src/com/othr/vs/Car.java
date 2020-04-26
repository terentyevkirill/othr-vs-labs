package com.othr.vs;

public class Car {
    private String licencePlate;
    private Color color;

    public Car(String licencePlate, Color color) {
        this.licencePlate = licencePlate;
        this.color = color;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "licencePlate='" + licencePlate + '\'' +
                ", color=" + color +
                '}';
    }
}
