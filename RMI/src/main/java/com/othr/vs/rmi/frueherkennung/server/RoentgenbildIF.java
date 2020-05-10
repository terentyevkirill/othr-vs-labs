package com.othr.vs.rmi.frueherkennung.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface RoentgenbildIF extends Remote {
    Date getAufnahmeVon() throws RemoteException;
    byte[] getRawData() throws RemoteException;
    String toPrint() throws RemoteException;
}
