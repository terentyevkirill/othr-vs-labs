package library.service;

import library.app.LibraryRestException;
import library.entity.Book;

import javax.ws.rs.*;
import java.util.*;

import static javax.ws.rs.core.MediaType.*;

@Path("books")
public class BookService {

    private static final Map<String, Book> books = new HashMap<>();
    private static final Map<Long, HashSet<String>> borrowedBooks = new HashMap<>();

    @GET
    @Path("/{isbn}")
    @Produces(APPLICATION_JSON)
    public Book getBookByIsbn(@PathParam("isbn") String isbn) {
        System.out.println("getBookByIsbn");
        if (books.containsKey(isbn)) {
            return books.get(isbn);
        } else {
            throw new LibraryRestException(404, "Nothing found for ISBN " + isbn);
        }
    }

    @POST
    @Path("borrow/{isbn}/{clientId}")
    @Produces(APPLICATION_JSON)
    public Collection<String> borrowBook(@PathParam("isbn") String isbn, @PathParam("clientId") long clientId) {
        System.out.println("borrowBook");
        if (books.containsKey(isbn) && ClientService.getClients().containsKey(clientId)) {
            if (borrowedBooks.containsKey(clientId)) {
                borrowedBooks.get(clientId).add(isbn);
            } else {
                borrowedBooks.put(clientId, new HashSet<>(List.of(isbn)));
            }
            return borrowedBooks.get(clientId);
        } else {
            throw new LibraryRestException(404, "Not found client or book for given query params");
        }
    }

    @GET
    @Path("borrow/{clientId}")
    @Produces(APPLICATION_JSON)
    public Collection<String> getBorrowedBooksForUser(@PathParam("clientId") long clientId) {
        System.out.println("getBorrowedBooksForUser");
        if (borrowedBooks.containsKey(clientId)) {
            return borrowedBooks.get(clientId);
        } else {
            throw new LibraryRestException(404, "No borrowed book found for client ID " + clientId);
        }
    }

    @POST
    @Path("")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Book addBook(Book book) {
        System.out.println("addBook");
        if (!books.containsKey(book.getIsbn())) {
            books.put(book.getIsbn(), book);
            return book;
        } else {
            throw new LibraryRestException(409, "Book with ISBN " + book.getIsbn() + " already exists");
        }
    }

    @GET
    @Path("")
    @Produces(APPLICATION_JSON)
    public Collection<Book> getAllBooks() {
        System.out.println("getAllBooks");
        return books.values();
    }
}
