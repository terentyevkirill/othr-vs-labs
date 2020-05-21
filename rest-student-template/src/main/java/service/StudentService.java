package service;

import entity.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class StudentService {

    private static int nextStudentId = 1;
    // private static Map<Integer, Student> studentDb;  // kann als interne Datenbank verwendet werden


    public Student matriculate(Student s) {

        // Methode annotieren und ausimplementieren und folgende "throw"-Anweisung löschen!
        throw new IllegalStateException("method 'matriculate' needs to be implemented first");

    }

    public Student exmatriculate(int studentId) {

        // Methode annotieren und ausimplementieren und folgende "throw"-Anweisung löschen!
        throw new IllegalStateException("method 'exmatriculate' needs to be implemented first");

    }

    public Student getStudentById(int studentId) {

        // Methode annotieren und ausimplementieren und folgende "throw"-Anweisung löschen!
        throw new IllegalStateException("method 'getStudentById' needs to be implemented first");

    }

    public Student updateStudentAccount(int studentId, Student newData) {

        // Methode annotieren und ausimplementieren und folgende "throw"-Anweisung löschen!
        throw new IllegalStateException("method 'updateStudentAccount' needs to be implemented first");

    }

    public Collection<Student> getAllStudents() {

        // Methode annotieren und ausimplementieren und folgende "throw"-Anweisung löschen!
        throw new IllegalStateException("method 'getAllStudents' needs to be implemented first");

    }

    public Collection<Student> getStudentsByRange(int fromStudentId, int toStudentId) {

        // Methode annotieren und ausimplementieren und folgende "throw"-Anweisung löschen!
        throw new IllegalStateException("method 'getStudentsByRange' needs to be implemented first");

    }
}
