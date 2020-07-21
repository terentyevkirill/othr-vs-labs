package com.othr.vs.threadbeispiel;

import java.util.Random;

public class Geldautomat implements Runnable {
    private String standort;
    private Konto konto;

    public Geldautomat(String standort, Konto konto) {
        this.standort = standort;
        this.konto = konto;
    }


    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            int beitrag = new Random().nextInt(2000);
            this.konto.einzahlen(beitrag);
            this.konto.auszahlen(beitrag);
            System.out.println(standort + ": " + this.konto.getKontostand());
            try {
                Thread.sleep(beitrag / 100);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }
}
