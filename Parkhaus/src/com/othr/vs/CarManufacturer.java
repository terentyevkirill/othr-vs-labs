package com.othr.vs;

import java.util.Random;

public class CarManufacturer implements Runnable {
    private Garage garage;

    public CarManufacturer(Garage garage) {
        this.garage = garage;
    }

    @Override
    public void run() { //Production
        int carCounter = 1;
        Random rnd = new Random();
        while (true) {
            try {
                long manufacturingTime = rnd.nextInt(2000);
                Thread.sleep(manufacturingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Car newCar = new Car("R-HF " + carCounter++, Color.values()[rnd.nextInt(Color.values().length)]);
            garage.driveIn(newCar);
            // After manufacturing
        }
    }
}
