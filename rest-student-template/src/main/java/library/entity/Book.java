package library.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Book {
    @XmlAttribute
    private String isbn;
    private String name;
    private String author;
    private int numberOfPages;
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date publicationDate;

    public Book() {
    }

    public Book(String isbn, String name, String author, int numberOfPages, Date publicationDate) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.numberOfPages = numberOfPages;
        this.publicationDate = publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        return getIsbn() != null ? getIsbn().equals(book.getIsbn()) : book.getIsbn() == null;
    }

    @Override
    public int hashCode() {
        return getIsbn() != null ? getIsbn().hashCode() : 0;
    }
}
