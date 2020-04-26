package com.othr.vs;

public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        Garage garage = new Garage(10);
        for (int i = 1; i <= 20; i++) {
            Thread thread = new Thread(new Car("R-FH " + i, garage));
            thread.setDaemon(true);
            thread.setName("R-FH " + i);
            thread.start();
        }

        Thread.sleep(60000);
        System.out.println(Thread.currentThread().getName() + ": End of simulation");

    }
}
