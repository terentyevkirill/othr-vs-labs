package com.othr.vs.rmi.smarttv.server;

import com.othr.vs.rmi.smarttv.client.CallbackIF;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TvControllerIF extends Remote {
    SendungIF getAktuelleSendung() throws RemoteException;

    void registriereAufnahmewunsch(AufnahmewunschIF aufnahmewunsch) throws RemoteException;

    void wechsleKanal(int neuerKanal) throws RemoteException;

    void aufnehmen(CallbackIF client) throws RemoteException;
}
