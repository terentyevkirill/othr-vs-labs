package com.othr.vs.threadbeispiel;

public class Simulation {
    public static void main(String[] args) {
        Konto konto = new Konto();
        Geldautomat ga1 = new Geldautomat("Unistrasse", konto);
        Geldautomat ga2 = new Geldautomat("Hauptbahnhof", konto);
        Thread thread1 = new Thread(ga1);
        Thread thread2 = new Thread(ga2);
        thread1.start();
        thread2.start();
    }
}
