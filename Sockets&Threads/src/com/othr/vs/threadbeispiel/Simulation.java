package com.othr.vs.threadbeispiel;

public class Simulation {
    public static void main(String[] args) {
        Konto konto = new Konto();
        Geldautomat ga1 = new Geldautomat("Unistrasse", konto);
        Geldautomat ga2 = new Geldautomat("Hauptbahnhof", konto);
        ga1.start();
        ga2.start();
    }
}
