package pl.edu.pk.lab3.services;

import org.springframework.stereotype.Service;
import pl.edu.pk.lab3.beans.Book;
import pl.edu.pk.lab3.helpers.ListHelper;

@Service
public class DashboardService {
    private ListHelper books;

    public DashboardService() {
        books = new ListHelper();
        books.add(new Book("Absalom, Absalom!", "William Faulkner", 	1936));
        books.add(new Book("Death Be Not Proud", "John Gunther", 1949));
        books.add(new Book("I Know Why the Caged Bird Sings", "Maya Angelou", 1969));
        books.add(new Book("Unweaving the Rainbow", "Richard Dawkins", 1998));
    }

    public ListHelper getBooks() {
        return books;
    }

    public Book getBook(int id) throws Exception {
        if(books.contains(id)) {
            return books.get(id);
        } else {
            throw new Exception("Book not found");
        }
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public Book removeBook(int id) throws Exception {
        Book book = getBook(id);
        books.removeIf(r -> r.id == id);
        return book;
    }
}
