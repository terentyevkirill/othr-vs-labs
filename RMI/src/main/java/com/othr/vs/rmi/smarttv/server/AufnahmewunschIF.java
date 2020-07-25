package com.othr.vs.rmi.smarttv.server;

import com.othr.vs.rmi.smarttv.server.entity.Codec;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

// server receives objects by reference,
// so interface should be defined on server side
public interface AufnahmewunschIF extends Remote {
    // only the methods, that should be accessible to server,
    // here defined!
    Date getStart() throws RemoteException;

    Date getEnde() throws RemoteException;

    Codec getCodec() throws RemoteException;

    // use ausgabe instead of toString(), bc we cannot override
    // with it throwing an Exception
    String toPrint() throws RemoteException;
}
