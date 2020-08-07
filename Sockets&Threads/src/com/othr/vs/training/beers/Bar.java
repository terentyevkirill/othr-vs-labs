package com.othr.vs.training.beers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bar {
    private final int capacity;
    private final Lock lock = new ReentrantLock();
    private final Condition full = lock.newCondition();
    private final Condition empty = lock.newCondition();

    private int beers;

    public Bar(int capacity) {
        this.capacity = capacity;
    }

    public void put() {
        try {
            lock.lock();
            while (beers == capacity) {
                System.out.println("    There are " + beers + " beers on the bar. Wait for take...");
                full.await();
            }
            beers++;
            empty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void take() {
        try {
            lock.lock();
            while (beers == 0) {
                System.out.println("    There are " + beers + " beers on the bar. Wait for put...");
                empty.await();
            }
            beers--;
            full.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
