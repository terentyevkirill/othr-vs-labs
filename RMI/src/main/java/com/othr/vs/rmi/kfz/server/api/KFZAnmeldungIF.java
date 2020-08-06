package com.othr.vs.rmi.kfz.server.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KFZAnmeldungIF extends Remote {
    Auto anmelden(Besitzer besitzer, Auto auto) throws RemoteException;
}
