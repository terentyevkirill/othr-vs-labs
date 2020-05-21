package service;

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

        if (fromStudentId == 0 || toStudentId == 0)
            return getAllStudents();
        else
            return getAllStudents()
                    .stream()
                    .filter(s -> s.getMatrikelNr() >= fromStudentId && s.getMatrikelNr() <= toStudentId)
                    .collect(Collectors.toList());
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
        return studentDb.remove(studentId);
    }

    @GET
    @Path("students/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Student getStudentById(@PathParam("id") int studentId) {
        return studentDb.get(studentId);
    }

    @PUT
    @Path("students/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Student updateStudentAccount(@PathParam("id") int studentId, Student newData) {
        newData.setMatrikelNr(studentId);   // if user gives to id or gives wrong
        studentDb.put(studentId, newData);  // old value
        return studentDb.get(studentId);    // return updated value
    }


}
