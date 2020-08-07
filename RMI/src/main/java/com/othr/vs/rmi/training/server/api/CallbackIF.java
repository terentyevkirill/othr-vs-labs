package com.othr.vs.rmi.training.server.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackIF extends Remote {
    void setBericht(BerichtIF bericht) throws RemoteException;
}
