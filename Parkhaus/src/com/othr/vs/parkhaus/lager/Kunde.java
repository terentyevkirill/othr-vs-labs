package com.othr.vs.parkhaus.lager;

import java.util.Random;

public class Kunde implements Runnable {
    private final Parkhaus parkhaus;

    public Kunde(Parkhaus parkhaus) {
        this.parkhaus = parkhaus;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while (true) {
            try {
                Thread.sleep(2000 + rnd.nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Auto newAuto = parkhaus.verkaufen();
            System.out.println("Gekauft: " + newAuto.toString());
        }
    }
}
