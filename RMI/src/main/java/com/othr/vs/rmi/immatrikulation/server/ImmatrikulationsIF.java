package com.othr.vs.rmi.immatrikulation.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ImmatrikulationsIF extends Remote {
    public Student immatrikulieren(Student student) throws RemoteException;
}
