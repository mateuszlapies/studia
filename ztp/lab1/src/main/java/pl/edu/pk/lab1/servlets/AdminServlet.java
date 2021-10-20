package pl.edu.pk.lab1.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pk.lab1.data.Book;
import pl.edu.pk.lab1.helpers.Books;
import pl.edu.pk.lab1.helpers.Html;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Stream;

@WebServlet(name = "AdminServlet", value = "/AdminServlet")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = (List<Book>) getServletContext().getAttribute("books");
        PrintWriter writer = response.getWriter();
        Html.PrintHead(writer, "Admin");
        Books.Print(writer, books, true);
        Books.Add(writer);
        Html.PrintFoot(writer);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> books = (List<Book>) getServletContext().getAttribute("books");
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.readValue(req.getReader().readLine(), Book.class);
        if(books.stream().filter(q -> q.title.equals(book.title) && q.author.equals(book.author) && q.year == book.year).count() > 0)
            books.removeIf(q -> q.title.equals(book.title) && q.author.equals(book.author) && q.year == book.year);
        else
            books.add(book);
        getServletContext().setAttribute("books", books);
        resp.sendRedirect("/");
    }
}
