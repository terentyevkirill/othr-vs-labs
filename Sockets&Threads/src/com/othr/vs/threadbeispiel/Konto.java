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
            // Sobald mindestens eine der beiden nachfolgenden Bedingungen erfüllt ist, muss notifyAll verwendet werden:
            // 1. Unterschiedliche Wartebedingungen (einzahlen und auszahlen)
            // 2. Potentielles Weiterlaufen mehrerer Threads
            monitor.notifyAll();
        }
    }

    public int getKontostand() {
        synchronized (monitor) {
            return kontostand;
        }
    }
}
