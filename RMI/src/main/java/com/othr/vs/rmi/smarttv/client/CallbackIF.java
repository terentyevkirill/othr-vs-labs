package com.othr.vs.rmi.smarttv.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackIF extends Remote {
    public void setAufnahmePfad(String pfad) throws RemoteException;
}
