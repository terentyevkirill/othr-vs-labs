package facebook.service;

import com.hazelcast.core.ReplicatedMap;
import facebook.app.RestException;
import facebook.entity.User;

import javax.ws.rs.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static javax.ws.rs.core.MediaType.*;
import static facebook.app.Server.*;

@Path("users")
public class UserService {

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public User getUserById(@PathParam("id") int userId) {
        System.out.println("getUserById: " + userId);
        User user;
        ReplicatedMap<Integer, User> users = hazelcast.getReplicatedMap(USERS_MAP_NAME);
        user = users.get(userId);
        if (user != null) {
            System.out.println(user + " taken from ReplicatedMap");
            return user;
        }

        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.first()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("username"));
                user.setDateOfBirth(rs.getDate("date_of_birth"));
                users.put(userId, user, 5, TimeUnit.MINUTES);
                System.out.println(user + " added to ReplicatedMap");
                return user;
            } else {
                throw new RestException(404, "User with ID: " + userId + " not found");
            }
        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    public Collection<User> deleteUserById(@PathParam("id") int userId) {
        System.out.println("deleteUserById: " + userId);
        ReplicatedMap<Integer, User> users = hazelcast.getReplicatedMap(USERS_MAP_NAME);
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, userId);
            if (stmt.executeUpdate() == 1) {
                // delete from replicated map, if present
                if (users.remove(userId) != null) {
                    System.out.println("User with ID: " + userId + " removed from ReplicatedMap");
                }
                return users.values();
            } else {
                throw new RestException(500, "Failed to delete user with ID: " + userId);
            }
        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

    @POST
    @Path("")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public User addUser(User user) {
        System.out.println("addUser: " + user);
        ReplicatedMap<Integer, User> users = hazelcast.getReplicatedMap(USERS_MAP_NAME);
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "INSERT INTO users (username, date_of_birth) VALUES (?, ?)";
            PreparedStatement stmt = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getName());
            stmt.setObject(2, LocalDate.ofInstant(user.getDateOfBirth().toInstant(), ZoneId.systemDefault()));
            if (stmt.executeUpdate() == 1) {
                ResultSet keys = stmt.getGeneratedKeys();
                while (keys.next()) {
                    user.setUserId(keys.getInt(1));
                }
                users.put(user.getUserId(), user, 5, TimeUnit.MINUTES);
                System.out.println(user + " added to ReplicatedMap");
                return user;
            } else {
                throw new RestException(500, "Failed to insert new user");
            }

        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

    @GET
    @Path("")
    @Produces(APPLICATION_JSON)
    public Collection<User> getAllUsers() {
        System.out.println("getAllUsers()");
        ReplicatedMap<Integer, User> users = hazelcast.getReplicatedMap(USERS_MAP_NAME);
        List<User> userList = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM users";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("username"));
                user.setDateOfBirth(rs.getDate("date_of_birth"));
                users.put(user.getUserId(), user, 5, TimeUnit.MINUTES);
                System.out.println(user + " added to ReplicatedMap");
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

}
