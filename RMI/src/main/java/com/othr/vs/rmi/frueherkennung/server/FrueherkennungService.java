package com.othr.vs.rmi.frueherkennung.server;

import com.othr.vs.rmi.frueherkennung.server.entity.Bericht;

import java.rmi.RemoteException;

public class FrueherkennungService implements FrueherkennungIF {
    @Override
    public Bericht analysieren(RoentgenbildIF roentgenbild) throws RemoteException {
        System.out.println("Empfangen: " + roentgenbild.toPrint());
        Bericht antwort = new Bericht(
                "Schnupfen",
                "Eine Woche Bettruhe und VS-Videos");
        System.out.println("Sende: " + antwort);
        return antwort;
    }
}
