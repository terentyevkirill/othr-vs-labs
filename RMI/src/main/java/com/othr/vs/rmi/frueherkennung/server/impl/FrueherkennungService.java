package com.othr.vs.rmi.frueherkennung.server.impl;

import com.othr.vs.rmi.frueherkennung.server.api.Bericht;
import com.othr.vs.rmi.frueherkennung.server.api.FrueherkennungIF;
import com.othr.vs.rmi.frueherkennung.server.api.Roentgenbild;

import java.rmi.RemoteException;

public class FrueherkennungService implements FrueherkennungIF {
    @Override
    public Bericht analysieren(Roentgenbild roentgenbilds) throws RemoteException {
        System.out.println("Empfangen: " + roentgenbilds);
        Bericht antwort = new Bericht(
                "Schnupfen",
                "Eine Woche Bettruhe und VS-Videos");
        System.out.println("Sende: " + antwort);
        return antwort;
    }
}
