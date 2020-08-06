package com.othr.vs.rmi.kfz.client;

import com.othr.vs.rmi.kfz.server.api.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class KFZAnmeldungClient implements CallbackIF {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            KFZAnmeldungIF serverStub = (KFZAnmeldungIF) registry.lookup("KFZAnmeldungsService");
            for (int i = 0; i < 10; i++) {
                Auto auto = new Auto("RF-H007" + i, "Audi", "TT");
                Besitzer besitzer = new Besitzer("12345" + i, "Kyrylo", "Terentiev");
                System.out.println("Anmelden " + auto + " für " + besitzer);
                AutoIF autoStub = (AutoIF) UnicastRemoteObject.exportObject(auto, 0);
                BesitzerIF besitzerStub = (BesitzerIF) UnicastRemoteObject.exportObject(besitzer, 0);
                CallbackIF client = new KFZAnmeldungClient();
                CallbackIF clientStub = (CallbackIF) UnicastRemoteObject.exportObject(client, 0);
                serverStub.anmelden(besitzerStub, autoStub, clientStub);
            }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBescheinigung(BescheinigungIF bescheinigung) throws RemoteException {
        System.out.println("Angemeldet: " + bescheinigung.toPrint());
    }
}
