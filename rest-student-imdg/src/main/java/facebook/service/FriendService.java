package facebook.service;

import com.hazelcast.core.MultiMap;
import facebook.app.RestException;

import javax.ws.rs.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.ws.rs.core.MediaType.*;
import static facebook.app.Server.*;

@Path("friends")
public class FriendService {

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Collection<Integer> getFriendsForUserId(@PathParam("id") int userId) {
        System.out.println("getFriendsForUserId: " + userId);
        MultiMap<Integer, Integer> friends = hazelcast.getMultiMap(FRIENDS_MULTIMAP_NAME);
        if (friends.containsKey(userId)) {
            System.out.println("Friends of user with ID: " + userId + " taken from MultiMap");
            return friends.get(userId);
        }
        List<Integer> friendList = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT friend_id FROM friends WHERE user_id = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int friendId = rs.getInt("friend_id");
                friends.put(userId, friendId);
                friendList.add(friendId);
                System.out.println("User with ID: " + friendId + " added to MultiMap as friend of user with ID: " + userId);
            }
            return friendList;
        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

    @POST
    @Path("/{user}/{friend}")
    public Collection<Integer> addFriendToUser(@PathParam("user") int userId, @PathParam("friend") int friendId) {
        System.out.println("addFriendToUser: " + friendId + " to " + userId);
        MultiMap<Integer, Integer> friends = hazelcast.getMultiMap(FRIENDS_MULTIMAP_NAME);
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            c.setAutoCommit(false);
            // add friend to user's friends
            String query = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            if (stmt.executeUpdate() == 1) {
                // add user to friend's friends
                PreparedStatement stmt2 = c.prepareStatement(query);
                stmt2.setInt(1, friendId);
                stmt2.setInt(2, userId);
                if (stmt2.executeUpdate() == 1) {
                    friends.put(userId, friendId);
                    friends.put(friendId, userId);
                    c.commit();
                    return friends.get(userId);
                } else {
                    c.rollback();
                    throw new RestException(500, "Failed to add friend with ID: " + userId + " to " + friendId + "'s friends");
                }
            } else {
                c.rollback();
                throw new RestException(500, "Failed to add friend with ID: " + friendId + " to " + userId + "'s friends");
            }
        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

    @DELETE
    @Path("/{user}/{friend}")
    public Collection<Integer> removeFriendFromUser(@PathParam("user") int userId, @PathParam("friend") int friendId) {
        System.out.println("removeFriendFromUser: " + friendId + " from " + userId);
        MultiMap<Integer, Integer> friends = hazelcast.getMultiMap(FRIENDS_MULTIMAP_NAME);
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            c.setAutoCommit(false);
            // remove friend from user's friends
            String query = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            if (stmt.executeUpdate() == 1) {
                // remove user from friend's friends
                PreparedStatement stmt2 = c.prepareStatement(query);
                stmt2.setInt(1, friendId);
                stmt2.setInt(2, userId);
                if (stmt2.executeUpdate() == 1) {
                    friends.remove(userId, friendId);
                    friends.remove(friendId, userId);
                    c.commit();
                    return friends.get(userId);
                } else {
                    c.rollback();
                    throw new RestException(500, "Failed to remove friend with ID: " + userId + " from " + friendId + "'s friends");
                }
            } else {
                c.rollback();
                throw new RestException(500, "Failed to remove friend with ID: " + friendId + " from " + userId + "'s friends");
            }
        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

}
