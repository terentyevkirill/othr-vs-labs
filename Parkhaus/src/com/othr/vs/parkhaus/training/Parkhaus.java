package com.othr.vs.parkhaus.training;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Parkhaus {
    private int anzahl = 0;
    private final int kapazitaet;
    private final Lock lock = new ReentrantLock();
    private final Condition einfahrer = lock.newCondition();
    private final Condition ausfahrer = lock.newCondition();

    public Parkhaus(int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }

    public void einfahren() {
        System.out.println("Belegt: " + this.anzahl);
        lock.lock();
        while (anzahl == kapazitaet) {
            try {
                System.out.println("   Warten an schranke zur Einfahrt: " + Thread.currentThread().getName());
                einfahrer.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Einfahren: " + Thread.currentThread().getName());
        this.anzahl++;
        einfahrer.signal();
        lock.unlock();
    }

    public void ausfahren() {
        System.out.println("Belegt: " + this.anzahl);
        lock.lock();
        while (anzahl <= 3) {
            try {
                System.out.println("   Warten an schranke zur Ausfahrt: " + Thread.currentThread().getName());
                ausfahrer.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Ausfahren: " + Thread.currentThread().getName());
        this.anzahl--;
        ausfahrer.signal();
        lock.unlock();
    }
}
