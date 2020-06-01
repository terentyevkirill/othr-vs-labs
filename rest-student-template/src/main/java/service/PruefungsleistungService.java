package service;

import app.OTHRestException;
import entity.Pruefungsleistung;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("pruefungsleistung")
public class PruefungsleistungService {

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Pruefungsleistung ablegen(Pruefungsleistung pruefungsleistung) {
        try (Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost/vs-08?useTimezone=true&serverTimezone=UTC", "root", "1234")) {
            c.setAutoCommit(false);
            try {
                String insertQuery = "INSERT INTO Pruefungsleistung (pruefungId, matrikelNr, versuch, note) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = c.prepareStatement(insertQuery);
                insertStmt.setString(1, pruefungsleistung.getPruefungId());
                insertStmt.setInt(2, pruefungsleistung.getMatrikelNr());
                insertStmt.setInt(3, pruefungsleistung.getVersuch());
                insertStmt.setFloat(4, pruefungsleistung.getNote());
                insertStmt.executeUpdate();

                String updateQuery = "UPDATE Student SET ects = ects + ? WHERE matrikelNr = ?";
                PreparedStatement updateStmt = c.prepareStatement(updateQuery);

                String selectQuery = "SELECT ects FROM Pruefung WHERE pruefungId = ?";
                PreparedStatement selectStmt = c.prepareStatement(selectQuery);
                selectStmt.setString(1, pruefungsleistung.getPruefungId());
                ResultSet rs = selectStmt.executeQuery();
                if (rs.first()) {
                    int ects = rs.getInt("ects");
                    updateStmt.setInt(1, ects);
                    updateStmt.setInt(2, pruefungsleistung.getMatrikelNr());
                    updateStmt.executeUpdate();
                    c.commit();
                    return pruefungsleistung;
                } else {
                    throw new OTHRestException(409, "Pruefungsleistung konnte nicht eingefügt werden");
                }
            } catch (Exception e) {
                c.rollback();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
