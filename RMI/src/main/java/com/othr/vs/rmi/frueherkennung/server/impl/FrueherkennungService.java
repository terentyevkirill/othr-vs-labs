package com.othr.vs.rmi.frueherkennung.server.impl;

import com.othr.vs.rmi.frueherkennung.server.api.Bericht;
import com.othr.vs.rmi.frueherkennung.server.api.CallbackIF;
import com.othr.vs.rmi.frueherkennung.server.api.FrueherkennungIF;
import com.othr.vs.rmi.frueherkennung.server.api.Roentgenbild;

import java.rmi.RemoteException;

public class FrueherkennungService implements FrueherkennungIF {
    @Override
    public void analysieren(Roentgenbild roentgenbild, CallbackIF callbackStub) throws RemoteException {
        System.out.println("Empfangen: " + roentgenbild);
        Runnable analyseTask = () -> {
            try {
                Thread.sleep(10000);
                Bericht antwort = new Bericht(
                        "Schnupfen",
                        "Eine Woche Bettruhe und VS-Videos");
                System.out.println("Sende: " + antwort);
                callbackStub.zustellen(antwort);
            } catch (InterruptedException | RemoteException e) {
                e.printStackTrace();
            }
        };
        new Thread(analyseTask).start();
    }
}
