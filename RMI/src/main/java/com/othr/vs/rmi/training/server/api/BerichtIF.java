package com.othr.vs.rmi.training.server.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface BerichtIF extends Remote {
    int getMatrikelNr() throws RemoteException;
    String getKursName() throws RemoteException;
    Date getKlausurDatum() throws RemoteException;
    Date getBerichtDatum() throws RemoteException;
    float getNote() throws RemoteException;
    String toPrint() throws RemoteException;
}
