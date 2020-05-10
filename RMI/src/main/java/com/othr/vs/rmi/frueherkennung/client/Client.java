package com.othr.vs.rmi.frueherkennung.client;

import com.othr.vs.rmi.frueherkennung.server.CallbackIF;
import com.othr.vs.rmi.frueherkennung.server.FrueherkennungIF;
import com.othr.vs.rmi.frueherkennung.server.entity.Bericht;
import com.othr.vs.rmi.frueherkennung.server.entity.Roentgenbild;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client implements CallbackIF {
    public static void main(String[] args) {
        Roentgenbild roentgenbild = new Roentgenbild("Alina Nakonechna");
        try {
            System.out.println("Roentgenbild an service sende: " + roentgenbild);
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            FrueherkennungIF serverStub = (FrueherkennungIF) registry.lookup("FrueherkennungService");
            CallbackIF client = new Client();
            CallbackIF callbackStub = (CallbackIF) UnicastRemoteObject.exportObject(client, 0);
            // eigentliche "remote"-Aufruf
            serverStub.analysieren(roentgenbild, callbackStub);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAntwort(Bericht bericht) throws RemoteException {
        System.out.println("Bericht empfangen: " + bericht);
    }
}
