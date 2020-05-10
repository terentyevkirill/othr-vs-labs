package com.othr.vs.rmi.frueherkennung.client;

import com.othr.vs.rmi.frueherkennung.server.api.BerichtIF;
import com.othr.vs.rmi.frueherkennung.server.api.FrueherkennungIF;
import com.othr.vs.rmi.frueherkennung.server.api.Roentgenbild;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        Roentgenbild roentgenbild = new Roentgenbild("Kyrylo Terentiev");
        try {
            System.out.println("Roentgenbild an service sende: " + roentgenbild);
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            FrueherkennungIF stub = (FrueherkennungIF) registry.lookup("FrueherkennungService");
            // eigentliche "remote"-Aufruf
            BerichtIF bericht = stub.analysieren(roentgenbild);
            System.out.println("Bericht empfangen: " + bericht.toPrint());
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
