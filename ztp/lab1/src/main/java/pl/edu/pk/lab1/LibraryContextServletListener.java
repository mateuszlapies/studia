package pl.edu.pk.lab1;

import pl.edu.pk.lab1.data.Book;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;

@WebListener()
public class LibraryContextServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("Absalom, Absalom!", "William Faulkner", 	1936));
        books.add(new Book("Death Be Not Proud", "John Gunther", 1949));
        books.add(new Book("I Know Why the Caged Bird Sings", "Maya Angelou", 1969));
        books.add(new Book("Unweaving the Rainbow", "Richard Dawkins", 1998));
        sce.getServletContext().setAttribute("books", books);
    }
}
