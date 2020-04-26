package com.othr.vs;

public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        Garage garage = new Garage(10);
        CarManufacturer manufacturer = new CarManufacturer(garage);
        Thread manufacturerThread = new Thread(manufacturer);
        manufacturerThread.setDaemon(true);
        manufacturerThread.start();

        for (int i = 1; i <= 5; i++) {
            Thread thread = new Thread(new CarDealer(garage));
            thread.setDaemon(true);
            thread.start();
        }

        Thread.sleep(30000);
        System.out.println(Thread.currentThread().getName() + ": End of simulation");

    }
}
