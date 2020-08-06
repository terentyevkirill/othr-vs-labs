package facebook.service;

import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import facebook.app.RestException;
import facebook.entity.User;
import facebook.mapreduce.*;
import org.javatuples.Pair;

import javax.ws.rs.*;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.ws.rs.core.MediaType.*;
import static facebook.app.Server.*;

@Path("friends")
public class FriendService {
    private final UserService userService = new UserService();


    @GET
    @Path("/all")
    @Produces(APPLICATION_JSON)
    public Map<Integer, Set<Integer>> getAllFriends() {
        System.out.println("getAllFriends");
        IMap<Integer, Set<Integer>> friends = hazelcast.getMap(FRIENDS_MULTIMAP_NAME);
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM friends";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int friendId = rs.getInt("friend_id");
                if (friends.containsKey(userId) && friends.get(userId) != null) {
                    friends.put(userId,
                            Stream.concat(friends.get(userId).stream(), Stream.of(friendId)).collect(Collectors.toSet()));
                } else {
                    friends.put(userId,
                            Stream.of(friendId).collect(Collectors.toSet()));
                }
            }
            return friends;
        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Collection<Integer> getFriendsForUserId(@PathParam("id") int userId) {
        System.out.println("getFriendsForUserId: " + userId);
        IMap<Integer, Set<Integer>> friends = hazelcast.getMap(FRIENDS_MULTIMAP_NAME);
        User user = userService.getUserById(userId);
        if (friends.containsKey(userId)) {
            System.out.println("Friends of user with ID: " + userId + " taken from MultiMap");
            return friends.get(userId);
        }
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT friend_id FROM friends WHERE user_id = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int friendId = rs.getInt("friend_id");
                friends.put(userId,
                        Stream.concat(friends.get(userId).stream(), Stream.of(friendId)).collect(Collectors.toSet()));

                if (friends.containsKey(friendId) && friends.get(friendId) != null) {
                    friends.put(userId,
                            Stream.concat(friends.get(userId).stream(), Stream.of(friendId)).collect(Collectors.toSet()));
                } else {
                    friends.put(userId,
                            Stream.of(friendId).collect(Collectors.toSet()));
                }
                System.out.println("User with ID: " + friendId + " added to MultiMap as friend of user with ID: " + userId);
            }

            return friends.get(userId);
        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

    @POST
    @Path("/{user}/{friend}")
    @Produces(APPLICATION_JSON)
    public Collection<Integer> addFriendToUser(@PathParam("user") int userId, @PathParam("friend") int friendId) {
        System.out.println("addFriendToUser: " + friendId + " to " + userId);
        IMap<Integer, Set<Integer>> friends = hazelcast.getMap(FRIENDS_MULTIMAP_NAME);
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
                    User user = userService.getUserById(userId);
                    User friend = userService.getUserById(friendId);
                    if (friends.containsKey(userId) && friends.get(userId) != null) {
                        friends.put(userId,
                                Stream.concat(friends.get(userId).stream(), Stream.of(friendId)).collect(Collectors.toSet()));
                    } else {
                        friends.put(userId,
                                Stream.of(friendId).collect(Collectors.toSet()));
                    }
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
    @Produces(APPLICATION_JSON)
    public Collection<Integer> removeFriendFromUser(@PathParam("user") int userId, @PathParam("friend") int friendId) {
        System.out.println("removeFriendFromUser: " + friendId + " from " + userId);
        IMap<Integer, Set<Integer>> friends = hazelcast.getMap(FRIENDS_MULTIMAP_NAME);
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
                    friends.put(userId, friends.get(userId).stream().filter(e -> e != friendId).collect(Collectors.toSet()));
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

    @GET
    @Path("/common")
    @Produces(APPLICATION_JSON)
    public Map<Pair<Integer, Integer>, Set<Integer>> getCommonFriends() {
        System.out.println("getCommonFriends");
        getAllFriends();
        IMap<Integer, Set<Integer>> friends = hazelcast.getMap(FRIENDS_MULTIMAP_NAME);

        friends.keySet().stream().sorted().forEach(key -> {
            System.out.print("Friends of " + key + ": ");
            System.out.println(Arrays.toString(friends.get(key).stream().sorted().toArray()));
        });

        JobTracker jobTracker = hazelcast.getJobTracker("default");

        KeyValueSource<Integer, Set<Integer>> source = KeyValueSource.fromMap(friends);

        Job<Integer, Set<Integer>> job = jobTracker.newJob(source);

        JobCompletableFuture<Map<Pair<Integer, Integer>, Set<Integer>>> jobCompletableFuture = job
                .mapper(new FriendMapper())
                .reducer(new FriendReducerFactory())
                .submit();

        try {
            return jobCompletableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RestException(500, e.getMessage());
        }
    }

}
