package com.othr.vs.rmi.kfz.server.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackIF extends Remote {
    void setBescheinigung(BescheinigungIF bescheinigung) throws RemoteException;
}
