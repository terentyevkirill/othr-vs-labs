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

public class SmartphoneApp {
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
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }
}
