package com.othr.vs.rmi.frueherkennung.server.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FrueherkennungIF extends Remote {
    void analysieren(Roentgenbild roentgenbild, CallbackIF callbackStub) throws RemoteException;
}
