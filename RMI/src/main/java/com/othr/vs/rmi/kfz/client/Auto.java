package com.othr.vs.rmi.kfz.client;

import com.othr.vs.rmi.kfz.server.api.AutoIF;

import java.rmi.RemoteException;

public class Auto implements AutoIF {
    private final String kennzeichnen;
    private final String hersteller;
    private final String modell;

    public Auto(String kennzeichnen, String hersteller, String modell) {
        this.kennzeichnen = kennzeichnen;
        this.hersteller = hersteller;
        this.modell = modell;
    }

    @Override
    public String getKennzeichnen() {
        return kennzeichnen;
    }


    public String getHersteller() {
        return hersteller;
    }

    public String getModell() {
        return modell;
    }

    @Override
    public String toPrint() throws RemoteException {
        return "Auto{" +
                "kennzeichnen='" + kennzeichnen + '\'' +
                '}';
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
                '}';
    }
}
