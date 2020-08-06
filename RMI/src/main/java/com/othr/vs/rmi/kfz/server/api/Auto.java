package com.othr.vs.rmi.kfz.server.api;

import java.io.Serializable;

public class Auto implements Serializable {
    private String kennzeichnen;
    private String hersteller;
    private String modell;
    private Besitzer besitzer;

    public Auto() {
    }

    public Auto(String kennzeichnen, String hersteller, String modell) {
        this.kennzeichnen = kennzeichnen;
        this.hersteller = hersteller;
        this.modell = modell;
    }

    public String getKennzeichnen() {
        return kennzeichnen;
    }


    public String getHersteller() {
        return hersteller;
    }

    public String getModell() {
        return modell;
    }

    public Besitzer getBesitzer() {
        return besitzer;
    }

    public void setBesitzer(Besitzer besitzer) {
        this.besitzer = besitzer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auto)) return false;

        Auto auto = (Auto) o;

        return getKennzeichnen().equals(auto.getKennzeichnen());
    }

    @Override
    public int hashCode() {
        return getKennzeichnen().hashCode();
    }

    @Override
    public String toString() {
        return "Auto{" +
                "kennzeichnen='" + kennzeichnen + '\'' +
                ", hersteller='" + hersteller + '\'' +
                ", modell='" + modell + '\'' +
                ", besitzer=" + besitzer +
                '}';
    }
}
