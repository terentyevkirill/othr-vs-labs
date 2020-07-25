package com.othr.vs.rmi.smarttv.client;

import com.othr.vs.rmi.smarttv.server.AufnahmewunschIF;
import com.othr.vs.rmi.smarttv.server.SendungIF;
import com.othr.vs.rmi.smarttv.server.TvControllerIF;
import com.othr.vs.rmi.smarttv.server.entity.Aufnahmewunsch;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class SmartphoneApp implements CallbackIF {
    private final List<String> aufnahmePfads = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            TvControllerIF stub = (TvControllerIF) registry.lookup("SmartTvService");

            Aufnahmewunsch wunsch = new Aufnahmewunsch();
            AufnahmewunschIF wunschStub = (AufnahmewunschIF) UnicastRemoteObject.exportObject(wunsch, 0);
            stub.registriereAufnahmewunsch(wunschStub);
            stub.wechsleKanal(3);
            SendungIF sendung = stub.getAktuelleSendung();
            sendung.getTitel();
            sendung.getBeschreibung();
            sendung.getStart();
            sendung.getEnde();
            System.out.println("SmartphoneApp aktuelle Sendung: " + sendung.toPrint());

            CallbackIF client = new SmartphoneApp();
            CallbackIF clientStub = (CallbackIF) UnicastRemoteObject.exportObject(client, 0);
            for (int i = 0; i < 5; i++) {
                stub.aufnehmen(clientStub);
            }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setAufnahmePfad(String pfad) throws RemoteException {
        System.out.println("Neuer Aufnahmepfad bekommen: " + pfad);
        this.aufnahmePfads.add(pfad);
        System.out.println("Alle Aufnahmepfads:");
        this.aufnahmePfads.forEach(System.out::println);
    }
}
