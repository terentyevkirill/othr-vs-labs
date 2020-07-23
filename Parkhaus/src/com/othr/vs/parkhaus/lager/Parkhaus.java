package com.othr.vs.parkhaus.lager;

import java.util.Deque;
import java.util.LinkedList;

public class Parkhaus {
    int kapazitaet;
    private Deque<Auto> autos = new LinkedList<>();
    private final Object monitor = new Object();

    public Parkhaus(int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }

    public void einfahren(Auto auto) {
        synchronized (monitor) {
            while (kapazitaet == autos.size()) {
                try {
                    System.out.println("    " + autos.size() + " autos im Parkhaus. " + "Warten an Schranke " + auto.toString());
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            autos.addLast(auto);
            System.out.println("Einfahren: " + auto.toString());
            monitor.notifyAll();
        }
    }

    public Auto verkaufen() {
        synchronized (monitor) {
            while (autos.isEmpty()) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Auto auto = autos.removeFirst();
            System.out.println("Ausfahren: " + auto.toString());
            monitor.notifyAll();
            return auto;
        }
    }
}
