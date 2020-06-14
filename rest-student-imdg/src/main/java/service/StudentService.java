package service;

import app.OTHRestException;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.ReplicatedMap;
import com.hazelcast.util.JsonUtil;
import de.othr.vs.xml.Adresse;
import de.othr.vs.xml.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static app.Server.*;

@Path("studentaffairs")
public class StudentService {

    @GET
    @Path("students")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Student> getAllStudents() {
        System.out.println("StudentService: getAllStudents()");
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM Student";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Collection<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student s = new Student();
                s.setMatrikelNr(rs.getInt("matrikelNr"));
                s.setVorname(rs.getString("vorname"));
                s.setNachname(rs.getString("nachname"));
                s.setEcts(rs.getInt("ects"));
                s.setAnschrift(new Adresse(
                        rs.getString("strasse"),
                        rs.getString("ort")
                ));
                students.add(s);
            }
            return students;
        } catch (SQLException throwables) {
            throw new OTHRestException(500, throwables.getMessage());
        }
    }

    //    @GET
//    @Path("students")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Student> getStudentsByRange(
            @QueryParam("from") int fromStudentId,
            @QueryParam("to") int toStudentId) {

        if (fromStudentId == 0 && toStudentId == 0)
            return getAllStudents();
        else if (toStudentId == 0 && fromStudentId > 0)
            return getAllStudents()
                    .stream()
                    .filter(s -> s.getMatrikelNr() >= fromStudentId)
                    .collect(Collectors.toSet());
        else
            return getAllStudents()
                    .stream()
                    .filter(s -> s.getMatrikelNr() >= fromStudentId && s.getMatrikelNr() <= toStudentId)
                    .collect(Collectors.toSet());
    }

    @POST
    @Path("students")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Student matriculate(Student s) {
        System.out.println("StudentService: matriculate()");
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            ReplicatedMap<Integer, Student> map = hazelcast.getReplicatedMap(STUDENTS_MAP_NAME);
            IAtomicLong studentId = hazelcast.getAtomicLong("matrikel-nr-counter");
            String getMaxMatrikelNrQuery = "SELECT MAX(matrikelNr) FROM Student";
            Statement maxMatrNrStmt = c.createStatement();
            ResultSet rs = maxMatrNrStmt.executeQuery(getMaxMatrikelNrQuery);
            rs.first();
            studentId.set(rs.getInt(1));
            s.setMatrikelNr((int) studentId.incrementAndGet());

            String query = "INSERT INTO Student (matrikelNr, vorname, nachname, ects, strasse, ort) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, s.getMatrikelNr());
            stmt.setString(2, s.getVorname());
            stmt.setString(3, s.getNachname());
            stmt.setInt(4, s.getEcts());
            stmt.setString(5, s.getAnschrift().getStrasse());
            stmt.setString(6, s.getAnschrift().getOrt());
            stmt.executeUpdate();
            System.out.println("StudentService: insert student into MySQL: " + s);

            // zusätzlich in Hazelcast data grid hinzufügen (für 5 Minuten)
            map.put(s.getMatrikelNr(), s, 5L, TimeUnit.MINUTES);
            System.out.println("StudentService: insert student into IMDG: " + s);

            return s;
        } catch (SQLException throwables) {
            throw new OTHRestException(500, throwables.getMessage());
        }
    }

    @DELETE
    @Path("students/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Student exmatriculate(@PathParam("id") int matrikelNr) {
        try (Connection c = DriverManager.getConnection(
                "jdbc:mysql://im-vm-011/vs-08", "vs-08", "vs-08-pw")) {
            Student s = getStudentById(matrikelNr);
            String query = "DELETE FROM Student WHERE matrikelNr = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, matrikelNr);
            stmt.executeUpdate();
            return s;
        } catch (SQLException throwables) {
            throw new OTHRestException(500, throwables.getMessage());
        }
    }

    @GET
    @Path("students/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Student getStudentById(@PathParam("id") int matrikelNr) {
        Student s = null;
        // zuerst aus Hazelcast data grid zu lesen versuchen
        ReplicatedMap<Integer, Student> map = hazelcast.getReplicatedMap(STUDENTS_MAP_NAME);
        s = map.get(matrikelNr);
        if (s != null) {
            System.out.println("StudentService: retrieve student from IMDG: " + s);
            return s;
        }

        // falls nicht gefunden, schlagen in MySQL nach
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            Statement stmt = c.createStatement();
            String query = "SELECT * FROM Student WHERE matrikelNr = " + matrikelNr;
            ResultSet rs = stmt.executeQuery(query);
            if (rs.first()) {
                s = new Student();
                s.setMatrikelNr(rs.getInt("matrikelnr"));
                s.setVorname(rs.getString("vorname"));
                s.setNachname(rs.getString("nachname"));
                s.setEcts(rs.getInt("ects"));
                Adresse anshrift = new Adresse(
                        rs.getString("strasse"),
                        rs.getString("ort"));
                s.setAnschrift(anshrift);
                System.out.println("StudentService: retrieve student from MySQL: " + s);

                // und speichern in IMDG
                map.put(s.getMatrikelNr(), s, 5L, TimeUnit.MINUTES);
                System.out.println("StudentService: insert student into IMDG: " + s);
                return s;
            } else {
                throw new OTHRestException(404, "Student mit ID " + matrikelNr + " existiert nicht");
            }
        } catch (SQLException throwables) {
            throw new OTHRestException(500, throwables.getMessage());
        }
    }

    @PUT
    @Path("students/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Student updateStudentAccount(@PathParam("id") int matrikelNr, Student newData) {
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "UPDATE Student SET vorname = ?, nachname = ?, ects = ?, strasse = ?, ort = ? WHERE matrikelNr = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setString(1, newData.getVorname());
            stmt.setString(2, newData.getNachname());
            stmt.setInt(3, newData.getEcts());
            stmt.setString(4, newData.getAnschrift().getStrasse());
            stmt.setString(5, newData.getAnschrift().getOrt());
            stmt.setInt(6, matrikelNr);
            stmt.executeUpdate();
            newData = getStudentById(matrikelNr);
            return newData;
        } catch (SQLException throwables) {
            throw new OTHRestException(500, throwables.getMessage());
        }
    }
}
