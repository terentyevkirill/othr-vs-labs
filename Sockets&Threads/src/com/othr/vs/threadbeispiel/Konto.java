package com.othr.vs.threadbeispiel;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Konto {
    private int kontostand;
    private final Lock lock = new ReentrantLock();
    private final Condition einzahler = lock.newCondition();
    private final Condition abheber = lock.newCondition();

    public void einzahlen(int betrag) {
        lock.lock();
        this.kontostand = this.kontostand + betrag;
        lock.unlock();
    }

    public void auszahlen(int betrag) {
        lock.lock();
        try {
            while (this.kontostand < betrag) {
                abheber.awaitUninterruptibly();
            }
            this.kontostand = this.kontostand - betrag;
        } finally {
            lock.unlock();
        }

    }

    public int getKontostand() {
        lock.lock();
        int result;
        try {
            result = this.kontostand;
            return result;
        } finally {
            lock.unlock();
        }
    }
}
