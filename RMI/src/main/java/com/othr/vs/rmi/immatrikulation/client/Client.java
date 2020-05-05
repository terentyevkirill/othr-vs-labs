package com.othr.vs.rmi.immatrikulation.client;

import com.othr.vs.rmi.immatrikulation.server.ImmatrikulationsIF;
import com.othr.vs.rmi.immatrikulation.server.Student;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            ImmatrikulationsIF server = (ImmatrikulationsIF) registry.lookup("ImmatrikulationsService");
            for (int i = 0; i < 10; i++) {
                Student student = server.immatrikulieren(new Student("Kyrylo Terentiev"));
                System.out.println("Student after immatrikulation: " + student.toString());
            }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
