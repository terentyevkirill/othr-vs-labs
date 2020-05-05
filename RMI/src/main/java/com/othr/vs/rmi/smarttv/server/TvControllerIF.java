package com.othr.vs.rmi.smarttv.server;

import com.othr.vs.rmi.smarttv.server.entity.Aufnahmewunsch;
import com.othr.vs.rmi.smarttv.server.entity.Sendung;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TvControllerIF extends Remote {
    Sendung getAktuelleSendung() throws RemoteException;

    void registriereAufnahmewunsch(Aufnahmewunsch aufnahmewunsch) throws RemoteException;

    void wechsleKanal(int neuerKanal) throws RemoteException;
}
