package com.othr.vs.rmi.kfz.server;

import com.othr.vs.rmi.kfz.server.api.*;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class KFZAnmeldungServer implements KFZAnmeldungIF {
    private int letzteBescheinigungsId = 1;

    public static void main(String[] args) {
        try {
            KFZAnmeldungIF server = new KFZAnmeldungServer();
            KFZAnmeldungIF serverStub = (KFZAnmeldungIF) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("KFZAnmeldungsService", serverStub);
            System.out.println("KFZAnmeldungServer ist gestartet...");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void anmelden(BesitzerIF besitzer, AutoIF auto, CallbackIF referenz) throws RemoteException {
        System.out.println("KFZAnmeldungServer: anmelden " + auto.toPrint() + " für " + besitzer.toPrint());
        try {
            Thread.sleep(1000 + new Random().nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Bescheinigung bescheinigung = new Bescheinigung(
                letzteBescheinigungsId++,
                besitzer.getSteuerId(),
                auto.getKennzeichnen(),
                "Fahrer sieht misstrauisch aus.",
                "Max Mustermann"
        );
        System.out.println("Angemeldet: " + bescheinigung);
        BescheinigungIF bescheinigungStub = (BescheinigungIF) UnicastRemoteObject.exportObject(bescheinigung, 0);
        referenz.setBescheinigung(bescheinigungStub);
    }
}
