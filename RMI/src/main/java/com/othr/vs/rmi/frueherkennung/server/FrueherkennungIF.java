package com.othr.vs.rmi.frueherkennung.server;

import com.othr.vs.rmi.frueherkennung.server.entity.Bericht;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FrueherkennungIF extends Remote {
    Bericht analysieren(RoentgenbildIF roentgenbild) throws RemoteException;
}
