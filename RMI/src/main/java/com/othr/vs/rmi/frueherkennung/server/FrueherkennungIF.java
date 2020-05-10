package com.othr.vs.rmi.frueherkennung.server;

import com.othr.vs.rmi.frueherkennung.server.entity.Bericht;
import com.othr.vs.rmi.frueherkennung.server.entity.Roentgenbild;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FrueherkennungIF extends Remote {
    BerichtIF analysieren(Roentgenbild roentgenbilds) throws RemoteException;
}
