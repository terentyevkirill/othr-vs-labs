package facebook.service;

import com.hazelcast.core.ReplicatedMap;
import facebook.app.RestException;
import facebook.entity.Post;

import javax.ws.rs.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static facebook.app.Server.*;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("posts")
public class PostService {

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Post getPostById(@PathParam("id") int postId) {
        System.out.println("getPostById: " + postId);
        Post post;
        ReplicatedMap<Integer, Post> posts = hazelcast.getReplicatedMap(POSTS_MAP_NAME);
        post = posts.get(postId);
        if (post != null) {
            System.out.println(post + " taken from ReplicatedMap");
            return post;
        }

        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM posts WHERE post_id = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            if (rs.first()) {
                post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setAuthorId(rs.getInt("author_id"));
                post.setText(rs.getString("text"));
                try {
                    post.setPublishedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("published")));
                    posts.put(postId, post, 5, TimeUnit.MINUTES);
                    System.out.println(post + " added to ReplicatedMap");
                    return post;
                } catch (ParseException e) {
                    throw new RestException(500, "Failed to parse publication date for post with ID: " + postId);
                }
            } else {
                throw new RestException(404, "Post with ID: " + postId + " not found");
            }
        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

    @POST
    @Path("")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Post addPost(Post post) {
        System.out.println("addPost: " + post);
        post.setPublishedAt(new Date());
        ReplicatedMap<Integer, Post> posts = hazelcast.getReplicatedMap(POSTS_MAP_NAME);
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "INSERT INTO posts (author_id, text, published) VALUES (?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, post.getAuthorId());
            stmt.setString(2, post.getText());
            stmt.setObject(3, LocalDateTime.ofInstant(post.getPublishedAt().toInstant(), ZoneId.systemDefault()));
            if (stmt.executeUpdate() == 1) {
                ResultSet keys = stmt.getGeneratedKeys();
                while (keys.next()) {
                    post.setPostId(keys.getInt(1));
                }
                posts.put(post.getPostId(), post, 5, TimeUnit.MINUTES);
                System.out.println(post + " added to ReplicatedMap");
                return post;
            } else {
                throw new RestException(500, "Failed to insert new post");
            }

        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Collection<Post> deletePostById(@PathParam("id") int postId) {
        System.out.println("deletePostById: " + postId);
        ReplicatedMap<Integer, Post> posts = hazelcast.getReplicatedMap(POSTS_MAP_NAME);
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "DELETE FROM posts WHERE post_id = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, postId);
            if (stmt.executeUpdate() == 1) {
                // delete from replicated map, if present
                if (posts.remove(postId) != null) {
                    System.out.println("Post with ID: " + postId + " removed from ReplicatedMap");
                }
                return posts.values();
            } else {
                throw new RestException(500, "Failed to delete post with ID: " + postId);
            }
        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }

    @GET
    @Path("")
    @Produces(APPLICATION_JSON)
    public Collection<Post> getAllPosts() {
        System.out.println("getAllPosts()");
        ReplicatedMap<Integer, Post> posts = hazelcast.getReplicatedMap(POSTS_MAP_NAME);
        List<Post> postList = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(DB_CONNECTION, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM posts";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setAuthorId(rs.getInt("author_id"));
                post.setText(rs.getString("text"));
                try {
                    post.setPublishedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("published")));
                    posts.put(post.getPostId(), post, 5, TimeUnit.MINUTES);
                    System.out.println(post + " added to ReplicatedMap");
                    postList.add(post);
                } catch (ParseException e) {
                    throw new RestException(500, "Failed to parse publication date for post with ID: " + post.getPostId());
                }
            }
            return postList;
        } catch (SQLException e) {
            throw new RestException(500, e.getMessage());
        }
    }
}
