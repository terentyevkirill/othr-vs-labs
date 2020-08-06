package com.othr.vs.parkhaus.training;

import java.util.Random;

public class Auto implements Runnable {
    private final String kennzeichnen;
    private final Parkhaus parkhaus;

    public Auto(String kennzeichnen, Parkhaus parkhaus) {
        this.kennzeichnen = kennzeichnen;
        this.parkhaus = parkhaus;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while (true) {
            try {
                Thread.sleep(2000 + rnd.nextInt(5000));
            } catch (InterruptedException e) {
                return;
            }
            parkhaus.einfahren();
            try {
                Thread.sleep(2000 + rnd.nextInt(5000));
            } catch (InterruptedException e) {
                return;
            }
            parkhaus.ausfahren();
        }
    }

    public String getKennzeichnen() {
        return kennzeichnen;
    }
}
