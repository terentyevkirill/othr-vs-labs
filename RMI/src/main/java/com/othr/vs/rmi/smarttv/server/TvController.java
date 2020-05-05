package com.othr.vs.rmi.smarttv.server;

import com.othr.vs.rmi.smarttv.server.entity.Sendung;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TvController implements TvControllerIF {
    private int aktuellerKanal = 1;

    public static void main(String[] args) {
        try {
            TvControllerIF instanz = new TvController();
            TvControllerIF stub = (TvControllerIF) UnicastRemoteObject.exportObject(instanz, 0);
            LocateRegistry.createRegistry(8080);    // nur für testen
            Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            registry.bind("SmartTvService", stub);
            System.out.println("TvController: up and listening...");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Sendung getAktuelleSendung() throws RemoteException {
        System.out.println("TvController: sende aktuelle Sendung");
        return new Sendung("Tatort", "einer stirbt, zwei ermittelt");
    }

    @Override
    public void registriereAufnahmewunsch(AufnahmewunschIF aufnahmewunsch) throws RemoteException {
        // call for testing -> on client side sout's are called
        aufnahmewunsch.getStart();
        aufnahmewunsch.getEnde();
        aufnahmewunsch.getCodec();
        System.out.println("TvController: Aufnahme von " + aufnahmewunsch.ausgabe());
    }

    @Override
    public void wechsleKanal(int neuerKanal) throws RemoteException {
        System.out.println("TvController: Wechsel von Kanal " + aktuellerKanal + " auf " + neuerKanal);
        this.aktuellerKanal = neuerKanal;
    }
}
