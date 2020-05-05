package com.othr.vs.rmi.smarttv.client;

import com.othr.vs.rmi.smarttv.server.TvControllerIF;
import com.othr.vs.rmi.smarttv.server.entity.Aufnahmewunsch;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SmartphoneApp {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            TvControllerIF stub = (TvControllerIF) registry.lookup("SmartTvService");
            System.out.println("App: " + stub.getAktuelleSendung());
            stub.registriereAufnahmewunsch(new Aufnahmewunsch());
            stub.wechsleKanal(17);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }
}
