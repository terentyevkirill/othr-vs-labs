package library.service;

import library.app.LibraryRestException;
import library.entity.Book;

import javax.ws.rs.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static javax.ws.rs.core.MediaType.*;
import static library.app.Server.*;

@Path("books")
public class BookService {

    @GET
    @Path("/{isbn}")
    @Produces(APPLICATION_JSON)
    public Book getBookByIsbn(@PathParam("isbn") String isbn) {
        try (Connection c = DriverManager.getConnection(DATABASE, USER, PASSWORD)) {
            String query = "SELECT * FROM books WHERE isbn = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.first()) {
                Book book = new Book();
                book.setIsbn(rs.getString("isbn"));
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                book.setNumberOfPages(rs.getInt("numberOfPages"));
                book.setPublicationDate(rs.getDate("publicationDate"));
                return book;
            } else {
                throw new LibraryRestException(404, "No book found for isbn: " + isbn);
            }
        } catch (SQLException e) {
            throw new LibraryRestException(500, e.getMessage());
        }
    }

    @POST
    @Path("borrow/{isbn}/{clientId}")
    @Produces(APPLICATION_JSON)
    public Collection<Book> borrowBook(@PathParam("isbn") String isbn, @PathParam("clientId") int clientId) {
        try (Connection c = DriverManager.getConnection(DATABASE, USER, PASSWORD)) {
            String query = "INSERT INTO booking (clientId, isbn) " +
                    "VALUES (?, ?)";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, clientId);
            stmt.setString(2, isbn);
            if (stmt.executeUpdate() != 1) {
                throw new LibraryRestException(500, "Booking could not be inserted");
            }
            return getBorrowedBooksForUser(clientId);
        } catch (SQLException e) {
            throw new LibraryRestException(500, e.getMessage());
        }
    }

    @GET
    @Path("borrow/{clientId}")
    @Produces(APPLICATION_JSON)
    public Collection<Book> getBorrowedBooksForUser(@PathParam("clientId") int clientId) {
        try (Connection c = DriverManager.getConnection(DATABASE, USER, PASSWORD)) {
            String query = "SELECT * FROM books INNER JOIN booking ON books.isbn = booking.isbn WHERE booking.clientId = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, clientId);
            ResultSet selectRs = stmt.executeQuery();
            List<Book> books = new ArrayList<>();
            while (selectRs.next()) {
                Book book = new Book();
                book.setIsbn(selectRs.getString("isbn"));
                book.setName(selectRs.getString("name"));
                book.setAuthor(selectRs.getString("author"));
                book.setNumberOfPages(selectRs.getInt("numberOfPages"));
                book.setPublicationDate(selectRs.getDate("publicationDate"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new LibraryRestException(500, e.getMessage());
        }
    }

    @POST
    @Path("")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Book addBook(Book book) {
        try (Connection c = DriverManager.getConnection(DATABASE, USER, PASSWORD)) {
            String query = "INSERT INTO books (isbn, name, author, numberOfPages, publicationDate) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getName());
            stmt.setString(3, book.getAuthor());
            stmt.setInt(4, book.getNumberOfPages());
            stmt.setObject(5, LocalDate.ofInstant(book.getPublicationDate().toInstant(), ZoneId.systemDefault()));
            if (stmt.executeUpdate() != 1) {
                throw new LibraryRestException(500, "Book could not be inserted");
            }
            return book;
        } catch (SQLException e) {
            throw new LibraryRestException(500, e.getMessage());
        }
    }

    @GET
    @Path("")
    @Produces(APPLICATION_JSON)
    public Collection<Book> getAllBooks() {
        try (Connection c = DriverManager.getConnection(DATABASE, USER, PASSWORD)) {
            Statement stmt = c.createStatement();
            String query = "SELECT * FROM books";
            ResultSet rs = stmt.executeQuery(query);
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                Book book = new Book();
                book.setIsbn(rs.getString("isbn"));
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                book.setNumberOfPages(rs.getInt("numberOfPages"));
                book.setPublicationDate(rs.getDate("publicationDate"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new LibraryRestException(500, e.getMessage());
        }
    }
}
