package com.othr.vs.rmi.immatrikulation.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements ImmatrikulationsIF {
    private static long letzteMatrikelNr = 1;

    public static void main(String[] args) {
        try {
            System.out.println("Instanz und Stub generieren");
            ImmatrikulationsIF server = new Server();
            ImmatrikulationsIF stub = (ImmatrikulationsIF) UnicastRemoteObject.exportObject(server, 0);
            System.out.println("An Registry binden");
            Registry registry = LocateRegistry.createRegistry(8080);
            registry.bind("ImmatrikulationsService", stub);
            System.out.println("Server ist bereit für Verbindungen von Client");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student immatrikulieren(Student student) throws RemoteException {
        // business logic
        student.setMatrikelNr(letzteMatrikelNr++);
        System.out.println("Lege Student in datenbank an: " + student.toString());
        return student;
    }
}
