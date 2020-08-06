package com.othr.vs.rmi.kfz.server;

import com.othr.vs.rmi.kfz.server.api.Auto;
import com.othr.vs.rmi.kfz.server.api.Besitzer;
import com.othr.vs.rmi.kfz.server.api.KFZAnmeldungIF;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class KFZAnmeldungServer implements KFZAnmeldungIF {
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
    public Auto anmelden(Besitzer besitzer, Auto auto) throws RemoteException {
        System.out.println("KFZAnmeldungServer: anmelden " + auto + " für " + besitzer);
        try {
            Thread.sleep(1000 + new Random().nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        auto.setBesitzer(besitzer);
        System.out.println("Angemeldet");
        return auto;
    }
}
