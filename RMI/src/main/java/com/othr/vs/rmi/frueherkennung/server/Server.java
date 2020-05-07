package com.othr.vs.rmi.frueherkennung.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            FrueherkennungIF serverImplementierung = new FrueherkennungService();
            // Stub und Skeleton gebaut
            FrueherkennungIF stub = (FrueherkennungIF) UnicastRemoteObject.exportObject(serverImplementierung, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("FrueherkennungService", stub);
            System.out.println("FrueherkennungService is running...");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }

    }
}
