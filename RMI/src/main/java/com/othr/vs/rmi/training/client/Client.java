package com.othr.vs.rmi.training.client;

import com.othr.vs.rmi.training.server.api.BerichtIF;
import com.othr.vs.rmi.training.server.api.CallbackIF;
import com.othr.vs.rmi.training.server.api.KlausurIF;
import com.othr.vs.rmi.training.server.api.KlausurServiceIF;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static com.othr.vs.rmi.training.server.api.KlausurServiceIF.*;

public class Client implements CallbackIF {
    private static int letzteMatrikelNr = 1;

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(PORT);
            KlausurServiceIF server = (KlausurServiceIF) registry.lookup(SERVICE_NAME);
            for (int i = 0; i < 10; i++) {
                CallbackIF instanz = new Client();
                CallbackIF referenz = (CallbackIF)UnicastRemoteObject.exportObject(instanz, 0);

                Klausur k = new Klausur(letzteMatrikelNr++, "Terentiev", "VS", "Sehr gute Klausur");
                System.out.println("Klausur senden: " + k.toString());

                KlausurIF klausurStub = (KlausurIF) UnicastRemoteObject.exportObject(k, 0);
                server.ueberpruefen(klausurStub, referenz);
            }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBericht(BerichtIF bericht) throws RemoteException {
        System.out.println("Bericht empfangen: " + bericht.toPrint());
    }
}
