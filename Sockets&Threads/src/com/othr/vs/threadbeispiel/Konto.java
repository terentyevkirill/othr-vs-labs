package com.othr.vs.threadbeispiel;

public class Konto {
    private int kontostand;
    private final Object monitor = new Object();

    public void einzahlen(int betrag) {
        synchronized (monitor) {
            this.kontostand = this.kontostand + betrag;
        }
    }

    public void auszahlen(int betrag) {
        synchronized (monitor) {
            while (this.kontostand < betrag) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    // ignore
                }
            }
            this.kontostand = this.kontostand - betrag;
            monitor.notifyAll();
        }
    }

    public int getKontostand() {
        synchronized (monitor) {
            return kontostand;
        }
    }
}
