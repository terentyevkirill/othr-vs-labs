package com.othr.vs.parkhaus.lager;

public class AutoVerkaufenSimulation {
    public static void main(String[] args) throws InterruptedException {
        Parkhaus parkhaus = new Parkhaus(10);
        Hersteller hersteller = new Hersteller(parkhaus);
        Thread herstellerThread = new Thread(hersteller);
        herstellerThread.setDaemon(true);
        herstellerThread.start();
        for (int i = 0; i < 5; i++) {
            Kunde kunde = new Kunde(parkhaus);
            Thread kundeThread = new Thread(kunde);
            kundeThread.setDaemon(true);
            kundeThread.start();
        }

        Thread.sleep(15000);
        System.out.println("Ende der Simulation");
    }
}
