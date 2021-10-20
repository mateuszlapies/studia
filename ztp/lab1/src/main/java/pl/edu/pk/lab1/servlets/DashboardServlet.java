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

@WebServlet(name = "DashboardServlet", value = "/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = (List<Book>) getServletContext().getAttribute("books");
        PrintWriter writer = response.getWriter();
        Html.PrintHead(writer, "Books");
        Books.Print(writer, books, false);
        Html.PrintFoot(writer);
    }
}
