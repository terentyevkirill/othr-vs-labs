package library.service;

import library.app.LibraryRestException;
import library.entity.Address;
import library.entity.Client;

import javax.ws.rs.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static library.app.Server.*;

@Path("clients")
public class ClientService {

    @GET
    @Path("")
    @Produces(APPLICATION_JSON)
    public Collection<Client> getAllClients() {
        try (Connection c = DriverManager.getConnection(DATABASE, USER, PASSWORD)) {
            Statement stmt = c.createStatement();
            String query = "SELECT * FROM clients";
            ResultSet rs = stmt.executeQuery(query);
            List<Client> clients = new ArrayList<>();
            while (rs.next()) {
                Client client = new Client();
                client.setClientId(rs.getInt("clientId"));
                client.setFirstName(rs.getString("firstName"));
                client.setLastName(rs.getString("lastName"));
                client.setDateOfBirth(rs.getDate("dateOfBirth"));
                client.setAddress(new Address(
                        rs.getString("street"),
                        rs.getString("city"),
                        rs.getString("zip")
                ));
                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            throw new LibraryRestException(500, e.getMessage());
        }
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Client getClientById(@PathParam("id") int id) {
        try (Connection c = DriverManager.getConnection(DATABASE, USER, PASSWORD)) {
            String query = "SELECT * FROM clients WHERE clientId = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.first()) {
                Client client = new Client();
                client.setClientId(rs.getInt("clientId"));
                client.setFirstName(rs.getString("firstName"));
                client.setLastName(rs.getString("lastName"));
                client.setDateOfBirth(rs.getDate("dateOfBirth"));
                client.setAddress(new Address(
                        rs.getString("street"),
                        rs.getString("city"),
                        rs.getString("zip")
                ));
                return client;
            } else {
                throw new LibraryRestException(404, "No client found for id: " + id);
            }
        } catch (SQLException e) {
            throw new LibraryRestException(500, e.getMessage());
        }
    }

    @POST
    @Path("")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Client addClient(Client client) {
        try (Connection c = DriverManager.getConnection(DATABASE, USER, PASSWORD)) {
            String query = "INSERT INTO clients (firstName, lastName, dateOfBirth, street, city, zip) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getLastName());
            stmt.setObject(3, LocalDate.ofInstant(client.getDateOfBirth().toInstant(), ZoneId.systemDefault()));
            stmt.setString(4, client.getAddress().getStreet());
            stmt.setString(5, client.getAddress().getCity());
            stmt.setString(6, client.getAddress().getZip());
            if (stmt.executeUpdate() != 1) {
                throw new LibraryRestException(500, "Client could not be inserted");
            }
            ResultSet keys = stmt.getGeneratedKeys();
            while (keys.next()) {
                client.setClientId(keys.getInt(1));
            }
            return client;
        } catch (SQLException e) {
            throw new LibraryRestException(500, e.getMessage());
        }
    }

}
