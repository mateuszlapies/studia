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
        books.add(new Book("lol", "lol", 1995));
        books.add(new Book("lol2", "lol4", 2020));
        books.add(new Book("lol10", "lol33", 1985));
        books.add(new Book("lol55", "lol1", 2004));
        sce.getServletContext().setAttribute("books", books);
    }
}
