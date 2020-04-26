package com.othr.vs;

public class Garage {
    private int capacity;
    private int occupiedPlaces = 0;
    private final Object monitor = new Object();

    public Garage(int capacity) {
        this.capacity = capacity;
    }

    public void driveIn() {
        synchronized (monitor) {
            // fachliche Sperrbedingung
            while (occupiedPlaces == capacity) {
                try {
                    System.out.println("    Warten an Schranke: " + Thread.currentThread().getName());
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            occupiedPlaces += 1;
            monitor.notifyAll();
        }
    }

    public void driveOut() {
        synchronized (monitor) {
            // fachliche Sperrbedingung
            while (occupiedPlaces == 2) {
                try {
                    System.out.println("    Warten auf Ausfahrt: " + Thread.currentThread().getName());
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            occupiedPlaces -= 1;
            monitor.notifyAll();   // nur EIN Auto ausfährt, d.h. kein notifyAll()
        }
    }
}
