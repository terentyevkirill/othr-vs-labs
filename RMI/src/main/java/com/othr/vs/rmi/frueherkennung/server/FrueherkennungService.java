package com.othr.vs.rmi.frueherkennung.server;

import com.othr.vs.rmi.frueherkennung.server.entity.Bericht;
import com.othr.vs.rmi.frueherkennung.server.entity.Roentgenbild;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FrueherkennungService implements FrueherkennungIF {
    @Override
    public BerichtIF analysieren(Roentgenbild roentgenbilds) throws RemoteException {
        System.out.println("Empfangen: " + roentgenbilds);
        Bericht antwort = new Bericht(
                "Schnupfen",
                "Eine Woche Bettruhe und VS-Videos");
        System.out.println("Sende: " + antwort.toPrint());
        BerichtIF antwortStub = (BerichtIF) UnicastRemoteObject.exportObject(antwort, 0);
        return antwortStub;
    }
}
