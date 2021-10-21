package pl.edu.pk.lab1.servlets;

import pl.edu.pk.lab1.data.Book;
import pl.edu.pk.lab1.helpers.Books;
import pl.edu.pk.lab1.helpers.Html;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
        try {
            String title = req.getParameter("title");
            String author = req.getParameter("author");
            int year = Integer.parseInt(req.getParameter("year"));
            Book book = new Book(title, author, year);
            List<Book> books = (List<Book>) getServletContext().getAttribute("books");
            books.add(book);
            getServletContext().setAttribute("books", books);
            resp.sendRedirect("/");
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int i = Integer.parseInt(req.getReader().readLine());
            List<Book> books = (List<Book>) getServletContext().getAttribute("books");
            books.remove(i);
            getServletContext().setAttribute("books", books);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
