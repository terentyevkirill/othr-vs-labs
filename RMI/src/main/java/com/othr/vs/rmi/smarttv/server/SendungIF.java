package com.othr.vs.rmi.smarttv.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface SendungIF extends Remote {
    String getTitel() throws RemoteException;
    String getBeschreibung() throws RemoteException;
    Date getStart() throws RemoteException;
    Date getEnde() throws RemoteException;
    String toPrint() throws RemoteException;
}
