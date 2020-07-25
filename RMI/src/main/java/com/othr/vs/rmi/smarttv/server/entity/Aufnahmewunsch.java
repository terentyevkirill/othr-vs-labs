package com.othr.vs.rmi.smarttv.server.entity;

import com.othr.vs.rmi.smarttv.server.AufnahmewunschIF;

import java.rmi.RemoteException;
import java.util.Date;

import static com.othr.vs.rmi.smarttv.server.entity.Codec.*;

public class Aufnahmewunsch implements AufnahmewunschIF {
    private Date start = new Date(), ende = new Date();
    private Codec codec = MPEG4;

    public Aufnahmewunsch(Date start, Date ende, Codec codec) {
        this.start = start;
        this.ende = ende;
        this.codec = codec;
    }

    public Aufnahmewunsch() {
    }


    @Override
    public Date getStart() throws RemoteException {
        System.out.println("Aufgabewunsch: getStart() aufgerufen");
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @Override
    public Date getEnde() throws RemoteException {
        System.out.println("Aufgabewunsch: getEnde() aufgerufen");
        return ende;
    }

    public void setEnde(Date ende) {
        this.ende = ende;
    }

    @Override
    public Codec getCodec() throws RemoteException {
        System.out.println("Aufgabewunsch: getCodec() aufgerufen");
        return codec;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }

    @Override
    public String toPrint() {
        System.out.println("Aufgabewunsch: toPrint() aufgerufen");
        return "Aufnahmewunsch{" +
                "start=" + start +
                ", ende=" + ende +
                ", codec=" + codec +
                '}';
    }
}
