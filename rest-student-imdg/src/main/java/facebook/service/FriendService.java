package facebook.service;

import com.hazelcast.core.IMap;
import com.hazelcast.core.ReplicatedMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import facebook.app.RestException;
import facebook.entity.User;
import facebook.mapreduce.FriendCollator;
import facebook.mapreduce.FriendMapper;
import facebook.mapreduce.FriendReducerFactory;
import org.javatuples.Pair;

import javax.ws.rs.*;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.*;
import static facebook.app.Server.*;

@Path("friends")
public class FriendService {
    private final UserService userService = new UserService();

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Collection<User> getFriendsForUserId(@PathParam("id") int userId) {
        System.out.println("getFriendsForUserId: " + userId);
        IMap<User, List<Integer>> friends = hazelcast.getMap(FRIENDS_MULTIMAP_NAME);
//        MultiMap<User, Integer> friends = hazelcast.getMultiMap(FRIENDS_MULTIMAP_NAME);
        Set<User> friendSet = new HashSet<>();
        User user = userService.getUserById(userId);
        if (friends.containsKey(user)) {
            System.out.println("Friends of user with ID: " + userId + " taken from MultiMap");
            friendSet.addAll(friends.get(user).stream().map(userService::getUserById).collect(Collectors.toCollection(ArrayList::new)));
        }
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT friend_id FROM friends WHERE user_id = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int friendId = rs.getInt("friend_id");
                User friend = userService.getUserById(friendId);

                if (friends.containsKey(user))
                    friends.get(user).add(friendId);
                else
                    friends.put(user, new ArrayList<>());
                if (friends.containsKey(friend))
                    friends.get(friend).add(userId);

                friends.put(friend, new ArrayList<>());
//                friends.put(user, friendId);
//                friends.put(friend, userId);
                friendSet.add(friend);
                System.out.println("User with ID: " + friendId + " added to MultiMap as friend of user with ID: " + userId);
            }
            return friendSet;
        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

    @POST
    @Path("/{user}/{friend}")
    @Produces(APPLICATION_JSON)
    public Collection<User> addFriendToUser(@PathParam("user") int userId, @PathParam("friend") int friendId) {
        System.out.println("addFriendToUser: " + friendId + " to " + userId);
        IMap<User, List<Integer>> friends = hazelcast.getMap(FRIENDS_MULTIMAP_NAME);
//        MultiMap<User, Integer> friends = hazelcast.getMultiMap(FRIENDS_MULTIMAP_NAME);
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
                    if (friends.containsKey(user))
                        friends.get(user).add(friendId);
                    else
                        friends.put(user, new ArrayList<>());
                    if (friends.containsKey(friend))
                        friends.get(friend).add(userId);
//                    friends.put(user, friendId);
//                    friends.put(friend, userId);
                    c.commit();
                    return friends.get(user).stream().map(userService::getUserById).collect(Collectors.toCollection(ArrayList::new));
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
    public Collection<User> removeFriendFromUser(@PathParam("user") int userId, @PathParam("friend") int friendId) {
        System.out.println("removeFriendFromUser: " + friendId + " from " + userId);
        IMap<User, List<Integer>> friends = hazelcast.getMap(FRIENDS_MULTIMAP_NAME);
//        MultiMap<User, Integer> friends = hazelcast.getMultiMap(FRIENDS_MULTIMAP_NAME);
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
                    User user = userService.getUserById(userId);
                    User friend = userService.getUserById(friendId);
                    if (friends.containsKey(user))
                        friends.get(user).remove(friendId);
                    if (friends.containsKey(friend))
                        friends.get(friend).remove(userId);
                    c.commit();
                    return friends.get(user).stream().map(userService::getUserById).collect(Collectors.toCollection(ArrayList::new));
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
    public Collection<Integer> getCommonFriends() {
        System.out.println("getCommonFriends");
        IMap<User, List<Integer>> friends = hazelcast.getMap(FRIENDS_MULTIMAP_NAME);

        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM friends";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            friends.clear();
            while (rs.next()) {
                User user = userService.getUserById(rs.getInt("user_id"));
                int friendId = rs.getInt("friend_id");
                if (!friends.containsKey(user))
                    friends.put(user, new ArrayList<>());
                friends.get(user).add(friendId);
            }

            JobTracker jobTracker = hazelcast.getJobTracker("default");

            KeyValueSource<User, List<Integer>> source = KeyValueSource.fromMap(friends);

            Job<User, List<Integer>> job = jobTracker.newJob(source);

            JobCompletableFuture<List<Integer>> jobCompletableFuture = job
                    .mapper(new FriendMapper())
                    .reducer(new FriendReducerFactory())
                    .submit(new FriendCollator());

            return jobCompletableFuture.get();

        } catch (InterruptedException | ExecutionException | SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

}
