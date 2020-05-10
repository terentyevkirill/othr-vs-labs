package com.othr.vs.rmi.frueherkennung.client;

import com.othr.vs.rmi.frueherkennung.server.api.CallbackIF;
import com.othr.vs.rmi.frueherkennung.server.api.FrueherkennungIF;
import com.othr.vs.rmi.frueherkennung.server.api.Roentgenbild;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client {
    public static void main(String[] args) {
        Roentgenbild roentgenbild = new Roentgenbild("Alina Nakonechna");
        try {
            System.out.println("Roentgenbild an service sende: " + roentgenbild);
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            FrueherkennungIF serverStub = (FrueherkennungIF) registry.lookup("FrueherkennungService");
            CallbackIF callbackEmpfaenger = new CallbackEmpfaenger();
            CallbackIF callbackStub = (CallbackIF) UnicastRemoteObject.exportObject(callbackEmpfaenger, 0);
            System.out.println("Analyse wurde gestartet...");
            // eigentliche "remote"-Aufruf
            serverStub.analysieren(roentgenbild, callbackStub);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }


}
