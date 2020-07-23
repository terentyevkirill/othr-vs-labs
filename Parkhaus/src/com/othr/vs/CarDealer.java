package com.othr.vs;

import java.util.Random;

public class CarDealer implements Runnable {
    private Garage garage;

    public CarDealer(Garage garage) {
        this.garage = garage;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while (true) {
            long saleTime = rnd.nextInt(7000);
            try {
                Thread.sleep(saleTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Car soldCar = garage.driveOut();
            System.out.println("New sale: " + soldCar.toString() + "\n");
        }
    }
}
