package com.othr.vs.rmi.kfz.server.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KFZAnmeldungIF extends Remote {
    BescheinigungIF anmelden(BesitzerIF besitzer, AutoIF auto) throws RemoteException;
}
