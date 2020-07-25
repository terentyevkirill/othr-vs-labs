package com.othr.vs.rmi.smarttv.server;

import com.othr.vs.rmi.smarttv.client.CallbackIF;
import com.othr.vs.rmi.smarttv.server.entity.Sendung;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

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
    public SendungIF getAktuelleSendung() throws RemoteException {
        System.out.println("TvController: sende aktuelle Sendung");
        Sendung sendung = new Sendung("Tatort", "einer stirbt, zwei ermittelt");
        return (SendungIF) UnicastRemoteObject.exportObject(sendung, 0);
    }

    @Override
    public void registriereAufnahmewunsch(AufnahmewunschIF aufnahmewunsch) throws RemoteException {
        // call for testing -> on client side sout's are called
        aufnahmewunsch.getStart();
        aufnahmewunsch.getEnde();
        aufnahmewunsch.getCodec();
        System.out.println("TvController: Aufnahme von " + aufnahmewunsch.toPrint());
    }

    @Override
    public void wechsleKanal(int neuerKanal) throws RemoteException {
        System.out.println("TvController: Wechsel von Kanal " + aktuellerKanal + " auf " + neuerKanal);
        this.aktuellerKanal = neuerKanal;
    }

    @Override
    public void aufnehmen(CallbackIF client) throws RemoteException {
        System.out.println("Aufnehmen starten");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Aufnehmen beendet");
        client.setAufnahmePfad("path-zum-video-" + new Random().nextInt(10));
    }
}
