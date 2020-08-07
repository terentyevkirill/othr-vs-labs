package com.othr.vs.rmi.training.server;

import com.othr.vs.rmi.training.server.api.BerichtIF;
import com.othr.vs.rmi.training.server.api.CallbackIF;
import com.othr.vs.rmi.training.server.api.KlausurIF;
import com.othr.vs.rmi.training.server.api.KlausurServiceIF;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class KlausurService implements KlausurServiceIF {

    public static void main(String[] args) {
        try {
            KlausurServiceIF serverInstanz = new KlausurService();
            KlausurServiceIF serverStub = (KlausurServiceIF) UnicastRemoteObject.exportObject(serverInstanz, 0);
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.bind(SERVICE_NAME, serverStub);
            System.out.println(SERVICE_NAME + " gestartet...");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ueberpruefen(KlausurIF k, CallbackIF referenz) throws RemoteException {
        Random rnd = new Random();
        System.out.println("Klausur empfangen: " + k.toPrint());
        try {
            TimeUnit.MILLISECONDS.sleep(3000 + rnd.nextInt(2500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        float rndNote = MIN_NOTE + rnd.nextFloat() * (MAX_NOTE - MIN_NOTE);

        Bericht b = new Bericht(
                k.getMatrikelNr(),
                k.getKursName(),
                k.getDatum(),
                rndNote,
                "Jobst"
        );
        System.out.println("Bericht erstellt: " + b.toString());
        BerichtIF berichtStub = (BerichtIF)UnicastRemoteObject.exportObject(b, 0);
        referenz.setBericht(berichtStub);
    }
}
