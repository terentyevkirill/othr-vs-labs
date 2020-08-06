package com.othr.vs.rmi.kfz.server.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AutoIF extends Remote {
    String getKennzeichnen() throws RemoteException;
    String toPrint() throws RemoteException;
}
