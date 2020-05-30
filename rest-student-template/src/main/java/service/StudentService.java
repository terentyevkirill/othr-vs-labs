package service;

import app.OTHRestException;
import entity.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Path("studentaffairs")
public class StudentService {

    private static int nextStudentId = 1;
    private static final Map<Integer, Student> studentDb = new HashMap<>();

    //    @GET
//    @Path("students")
    public Collection<Student> getAllStudents() {
        return studentDb.values();
    }

    @GET
    @Path("students")
    // @Produces und @Consumes sind hier nicht notwendig, sind aber für
    // demonstration angegeben
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
        s.setMatrikelNr(nextStudentId++);
        studentDb.put(s.getMatrikelNr(), s);
        return s;
    }

    @DELETE
    @Path("students/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Student exmatriculate(@PathParam("id") int studentId) {
        if (studentDb.containsKey(studentId)) {
            return studentDb.remove(studentId);
        } else {
            throw new OTHRestException(404, "Student mit ID " + studentId + " ist nicht immatrikuliert");
        }
    }

    @GET
    @Path("students/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Student getStudentById(@PathParam("id") int studentId) {
        if (studentDb.containsKey(studentId)) {
            return studentDb.get(studentId);
        } else {
            throw new OTHRestException(404, "Student mit ID " + studentId + " ist nicht immatrikuliert");
        }
    }

    @PUT
    @Path("students/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Student updateStudentAccount(@PathParam("id") int studentId, Student newData) {
        if (studentDb.containsKey(studentId)) {
            newData.setMatrikelNr(studentId);   // if user gives to id or gives wrong
            studentDb.put(studentId, newData);  // old value
            return studentDb.get(studentId);    // return updated value
        } else {
            throw new OTHRestException(404, "Student mit ID " + studentId + " ist nicht immatrikuliert");
        }

    }


}
