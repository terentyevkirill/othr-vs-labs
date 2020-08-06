package com.othr.vs.rmi.kfz.server.api;

import java.io.Serializable;

public class Besitzer implements Serializable {
    private String steuerId;
    private String vorname;
    private String nachname;

    public Besitzer() {
    }

    public Besitzer(String steuerId, String vorname, String nachname) {
        this.steuerId = steuerId;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public String getSteuerId() {
        return steuerId;
    }

    public void setSteuerId(String steuerId) {
        this.steuerId = steuerId;
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
