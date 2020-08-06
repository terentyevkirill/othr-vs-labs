package com.othr.vs.rmi.kfz.server.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface BescheinigungIF extends Remote {
    int getBescheinigungsId() throws RemoteException;
    String getBesitzerSteuerId() throws RemoteException;
    String getAutokennzeichnen() throws RemoteException;
    Date getAnmeldungsDatum() throws RemoteException;
    String toPrint() throws RemoteException;
}
