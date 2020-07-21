package com.othr.vs.threadbeispiel;

public class Konto {
    private int kontostand;

    public void einzahlen(int beitrag) {
        this.kontostand = this.kontostand + beitrag;
    }

    public void auszahlen(int beitrag) {
        this.kontostand = this.kontostand - beitrag;
    }

    public int getKontostand() {
        return kontostand;
    }
}
