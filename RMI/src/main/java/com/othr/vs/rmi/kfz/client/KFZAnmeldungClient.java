package com.othr.vs.rmi.kfz.client;

import com.othr.vs.rmi.kfz.server.api.Auto;
import com.othr.vs.rmi.kfz.server.api.Besitzer;
import com.othr.vs.rmi.kfz.server.api.KFZAnmeldungIF;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class KFZAnmeldungClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            KFZAnmeldungIF serverStub = (KFZAnmeldungIF) registry.lookup("KFZAnmeldungsService");
            for (int i = 0; i < 10; i++) {
                Auto auto = new Auto("RF-H007" + i, "Audi", "TT");
                Besitzer besitzer = new Besitzer("12345" + i, "Kyrylo", "Terentiev");
                auto = serverStub.anmelden(besitzer, auto);
                System.out.println("Auto nach der KFZ-Anmeldung: " + auto);
            }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
