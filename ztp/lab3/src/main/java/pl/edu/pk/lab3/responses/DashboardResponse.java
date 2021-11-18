package pl.edu.pk.lab3.responses;

import pl.edu.pk.lab3.beans.Book;
import pl.edu.pk.lab3.beans.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DashboardResponse extends Response {
    public List<Book> books;

    public DashboardResponse(List<Book> books) {
        super(200, "Books");
        this.books = books;
    }

    public DashboardResponse(Book book, Status status) {
        super(200, statusString(status));
        this.books = new ArrayList<>();
        this.books.add(book);
    }

    private static String statusString(Status status) {
        switch (status) {
            default:
            case GET: return "Book";
            case POST: return "Book added";
            case DELETE: return "Book deleted";
        }
    }
}
