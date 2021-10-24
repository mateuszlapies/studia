package pl.edu.pk.lab2.responses;

import pl.edu.pk.lab2.beans.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DashboardResponse extends Response {
    public List<Book> books;

    public DashboardResponse() {}

    public DashboardResponse(List<Book> books) {
        super(200, "Books list");
        this.books = books;
    }

    public DashboardResponse(Book book, boolean added) {
        super(200, added ? "Book added" : "Book already exists");
        this.books = new ArrayList<>();
        this.books.add(book);
    }

    public DashboardResponse(Optional<Book> book) {
        super(200, book.isPresent() ? "Book deleted" : "Book not found");
        if(book.isPresent()) {
            this.books = new ArrayList<>();
            this.books.add(book.get());
        }
    }
}
