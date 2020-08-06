package com.othr.vs.rmi.kfz.server.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BesitzerIF extends Remote {
    String getSteuerId() throws RemoteException;
    String toPrint() throws RemoteException;
}
