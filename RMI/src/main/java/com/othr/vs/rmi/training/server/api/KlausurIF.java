package com.othr.vs.rmi.training.server.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface KlausurIF extends Remote {
    int getMatrikelNr() throws RemoteException;
    String getKursName() throws RemoteException;
    Date getDatum() throws RemoteException;
    String getKlausurInhalt() throws RemoteException;
    String toPrint() throws RemoteException;
}
