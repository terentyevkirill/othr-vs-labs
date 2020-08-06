package com.othr.vs.parkhaus.training;

public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        Parkhaus parkhaus = new Parkhaus(10);
        for (int i = 0; i < 20; i++) {
            Auto auto = new Auto("RF-H" + i, parkhaus);
            Thread t = new Thread((Runnable) auto, auto.getKennzeichnen());
            t.setDaemon(true);
            t.start();
        }

        Thread.sleep(60000);
        System.out.println("Ende der Simulation");
    }
}
