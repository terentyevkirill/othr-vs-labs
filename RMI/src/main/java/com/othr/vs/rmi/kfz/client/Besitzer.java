package com.othr.vs.rmi.kfz.client;

import com.othr.vs.rmi.kfz.server.api.BesitzerIF;

import java.rmi.RemoteException;

public class Besitzer implements BesitzerIF {
    private final String steuerId;
    private String vorname;
    private String nachname;

    public Besitzer(String steuerId, String vorname, String nachname) {
        this.steuerId = steuerId;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    @Override
    public String getSteuerId() {
        return steuerId;
    }

    @Override
    public String toPrint() throws RemoteException {
        return "Besitzer{" +
                "steuerId='" + steuerId + '\'' +
                '}';
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Besitzer)) return false;

        Besitzer besitzer = (Besitzer) o;

        return getSteuerId().equals(besitzer.getSteuerId());
    }

    @Override
    public int hashCode() {
        return getSteuerId().hashCode();
    }

    @Override
    public String toString() {
        return "Besitzer{" +
                "steuerId='" + steuerId + '\'' +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                '}';
    }
}
