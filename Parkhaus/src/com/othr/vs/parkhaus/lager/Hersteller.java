package com.othr.vs.parkhaus.lager;

import java.util.Random;

public class Hersteller implements Runnable {
    private final Parkhaus parkhaus;

    public Hersteller(Parkhaus parkhaus) {
        this.parkhaus = parkhaus;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        int autoCounter = 1;
        while (true) {
            try {
                Thread.sleep(1000 + rnd.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Auto newAuto = new Auto("R-FH " + autoCounter++);
            System.out.println("Hergestellt: " + newAuto.toString());
            parkhaus.einfahren(newAuto);
        }
    }
}
