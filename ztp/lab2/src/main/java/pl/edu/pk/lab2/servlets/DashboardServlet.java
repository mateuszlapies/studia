package pl.edu.pk.lab2.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DashboardServlet", value = "/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*List<Book> books = (List<Book>) getServletContext().getAttribute("books");
        PrintWriter writer = response.getWriter();
        Html.PrintHead(writer, "Books");
        Books.Print(writer, books, false);
        Html.PrintFoot(writer);*/
    }
}
