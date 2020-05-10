package com.othr.vs.rmi.frueherkennung.client;

import com.othr.vs.rmi.frueherkennung.server.api.Bericht;
import com.othr.vs.rmi.frueherkennung.server.api.CallbackIF;

import java.rmi.RemoteException;

public class CallbackEmpfaenger implements CallbackIF {
    @Override
    public void zustellen(Bericht bericht) throws RemoteException {
        System.out.println("Bericht empfangen: " + bericht);
    }
}
