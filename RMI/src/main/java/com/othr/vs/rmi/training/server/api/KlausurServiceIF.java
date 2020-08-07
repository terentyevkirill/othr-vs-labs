package com.othr.vs.rmi.training.server.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KlausurServiceIF extends Remote {
    int PORT = 1099;
    String SERVICE_NAME = "KlausurService";
    float MIN_NOTE = 5.0F;
    float MAX_NOTE = 1.0F;
    void ueberpruefen(KlausurIF klausur, CallbackIF referenz) throws RemoteException;
}
