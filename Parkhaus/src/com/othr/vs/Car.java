package com.othr.vs;

import java.util.Random;

public class Car implements Runnable {
    private String licencePlate;
    private Garage garage;

    public Car(String licencePlate, Garage garage) {
        this.licencePlate = licencePlate;
        this.garage = garage;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                garage.driveIn();
                System.out.println("Einfahrt: " + licencePlate);
                long sleepTime = 1000 + random.nextInt(4000);
                Thread.sleep(sleepTime);
                garage.driveOut();
                System.out.println("Ausfahrt: " + licencePlate);
                sleepTime = 1000 + random.nextInt(4000);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
