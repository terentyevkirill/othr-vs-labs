package com.othr.vs.rmi.frueherkennung.client;

import com.othr.vs.rmi.frueherkennung.server.FrueherkennungIF;
import com.othr.vs.rmi.frueherkennung.server.RoentgenbildIF;
import com.othr.vs.rmi.frueherkennung.server.entity.Bericht;
import com.othr.vs.rmi.frueherkennung.server.entity.Roentgenbild;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            FrueherkennungIF serverStub = (FrueherkennungIF) registry.lookup("FrueherkennungService");
            RoentgenbildIF roentgenbild = new Roentgenbild("Kyrylo Terentiev");
            System.out.println("Roentgenbild an service sende: " + roentgenbild);
            RoentgenbildIF roentgenbildStub = (RoentgenbildIF) UnicastRemoteObject.exportObject(roentgenbild, 0);
            // eigentliche "remote"-Aufruf
            Bericht bericht = serverStub.analysieren(roentgenbildStub);
            System.out.println("Bericht empfangen: " + bericht);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
