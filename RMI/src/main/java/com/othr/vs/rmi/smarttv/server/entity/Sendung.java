package com.othr.vs.rmi.smarttv.server.entity;

import com.othr.vs.rmi.smarttv.server.SendungIF;

import java.rmi.RemoteException;
import java.util.Date;

public class Sendung implements SendungIF {
    private String titel;
    private String beschreibung;
    private Date start = new Date(), ende = new Date();

    public Sendung(String titel, String beschreibung) {
        this.titel = titel;
        this.beschreibung = beschreibung;
    }

    public Sendung() {
    }

    @Override
    public String getTitel() throws RemoteException {
        System.out.println("Sendung: getTitel() aufgerufen");
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    @Override
    public String getBeschreibung() throws RemoteException {
        System.out.println("Sendung: getBeschreibung() aufgerufen");
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public Date getStart() throws RemoteException {
        System.out.println("Sendung: getStart() aufgerufen");
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @Override
    public Date getEnde() throws RemoteException {
        System.out.println("Sendung: getEnde() aufgerufen");
        return ende;
    }

    @Override
    public String toPrint() throws RemoteException {
        System.out.println("Sendung: toPrint() aufgerufen");
        return "Sendung{" +
                "titel='" + titel + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", start=" + start +
                ", ende=" + ende +
                '}';
    }

    public void setEnde(Date ende) {
        this.ende = ende;
    }

    @Override
    public String toString() {
        return "Sendung{" +
                "titel='" + titel + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", start=" + start +
                ", ende=" + ende +
                '}';
    }
}
