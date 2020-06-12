package service;

import app.OTHRestException;
import entity.Pruefungsleistung;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@Path("studentaffairs")
public class PruefungsleistungService {
    @PUT
    @Path("students/{sid}/examresults")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Pruefungsleistung addExam(@PathParam("sid") int studentId, Pruefungsleistung pl) {
        pl.setMatrikelNr(studentId);
        // for mysql v8 add ?useTimezone=true&serverTimezone=UTC
        try (Connection c = DriverManager.getConnection(
                "jdbc:mysql://im-vm-011/vs-08", "vs-08", "vs-08-pw")) {
            c.setAutoCommit(false);
            try {
                // get ects-number from Pruefung table
                String query1 = "SELECT ects FROM Pruefung WHERE pruefungId = ?";
                PreparedStatement stmt1 = c.prepareStatement(query1);
                stmt1.setString(1, pl.getPruefungId());
                ResultSet ectsResult = stmt1.executeQuery();
                if (ectsResult.first()) {
                    // if found - update Student table (increase student's ects number)
                    int ects = ectsResult.getInt("ects");
                    String query2 = "UPDATE Student SET ects = ects + ? WHERE matrikelNr = ?";
                    PreparedStatement stmt2 = c.prepareStatement(query2);
                    stmt2.setInt(1, ects);
                    stmt2.setInt(2, studentId);
                    if (stmt2.executeUpdate() == 1) {
                        // if exactly 1 row was updated, add row to Pruefungsleistung table
                        String query3 = "INSERT INTO Pruefungsleistung (pruefungId, matrikelNr, versuch, note) VALUES (?, ?, ?, ?)";
                        PreparedStatement stmt3 = c.prepareStatement(query3);
                        stmt3.setString(1, pl.getPruefungId());
                        stmt3.setInt(2, pl.getMatrikelNr());
                        stmt3.setInt(3, pl.getVersuch());
                        stmt3.setString(4, pl.getNote());
                        if (stmt3.executeUpdate() == 1) {
                            // if exactly 1 row was inserted, commit changes and return object
                            c.commit();
                            return pl;
                        } else {
                            c.rollback();
                            throw new OTHRestException(500, "Pruefungsleistung konnte nicht eingetragen werden");
                        }
                    } else {
                        c.rollback();
                        throw new OTHRestException(500, "ECTS konnten nicht geandert werden");
                    }
                } else {
                    c.rollback();
                    throw new OTHRestException(404, "Pruefung mit ID " + pl.getPruefungId() + " existiert nicht");
                }
            } catch (SQLException e) {
                c.rollback();
                throw new OTHRestException(500, e.getMessage());
            }
        } catch (SQLException throwables) {
            throw new OTHRestException(500, throwables.getMessage());
        }
    }

    @GET
    @Path("students/{sid}/examresults")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Pruefungsleistung> getExamsByStudentId(@PathParam("sid") int studentId) {
        try (Connection c = DriverManager.getConnection(
                "jdbc:mysql://im-vm-011/vs-08", "vs-08", "vs-08-pw")) {
            String query = "SELECT * FROM Pruefungsleistung WHERE matrikelNr = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            Collection<Pruefungsleistung> pruefungsleistungen = new ArrayList<>();
            while (rs.next()) {
                Pruefungsleistung p = new Pruefungsleistung();
                p.setId(rs.getInt("id"));
                p.setMatrikelNr(rs.getInt("matrikelNr"));
                p.setPruefungId(rs.getString("pruefungId"));
                p.setVersuch(rs.getInt("versuch"));
                p.setNote(rs.getString("note"));
                pruefungsleistungen.add(p);
            }
            return pruefungsleistungen;
        } catch (SQLException throwables) {
            throw new OTHRestException(500, throwables.getMessage());
        }
    }
}
