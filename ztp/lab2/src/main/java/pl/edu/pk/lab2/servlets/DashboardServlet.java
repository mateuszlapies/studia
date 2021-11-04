package pl.edu.pk.lab2.servlets;

import com.google.gson.Gson;
import pl.edu.pk.lab2.beans.Book;
import pl.edu.pk.lab2.beans.Role;
import pl.edu.pk.lab2.beans.User;
import pl.edu.pk.lab2.exceptions.UnauthorizedException;
import pl.edu.pk.lab2.helpers.Helper;
import pl.edu.pk.lab2.helpers.ListHelper;
import pl.edu.pk.lab2.responses.DashboardResponse;
import pl.edu.pk.lab2.responses.ExceptionResponse;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "DashboardServlet", value = "/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("In DashboardServlet GET");
        Helper.addResponseAttributes(resp);
        Gson gson = new Gson();
        try {
            List<Book> books = getBooks(req);
            gson.toJson(new DashboardResponse(books), resp.getWriter());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            gson.toJson(new ExceptionResponse(500, e), resp.getWriter());
        }
        System.out.println("Out DashboardServlet GET");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("In DashboardServlet POST");
        Helper.addResponseAttributes(resp);
        Gson gson = new Gson();
        try {
            verifyAdmin(req);
            Book book = gson.fromJson(req.getReader(), Book.class);
            ListHelper books = getBooks(req);
            boolean added = addBook(books, book);
            if (added)
                setBooks(req, books);
            gson.toJson(new DashboardResponse(book, added), resp.getWriter());
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            resp.setStatus(401);
            gson.toJson(new ExceptionResponse(401, e), resp.getWriter());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            gson.toJson(new ExceptionResponse(500, e), resp.getWriter());
        }
        System.out.println("Out DashboardServlet POST");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("In DashboardServlet DELETE");
        Helper.addResponseAttributes(resp);
        Gson gson = new Gson();
        try {
            verifyAdmin(req);
            int id = Integer.parseInt(req.getParameter("id"));
            ListHelper books = getBooks(req);
            Optional<Book> book = removeBook(books, id);
            if (book.isPresent())
                setBooks(req, books);
            gson.toJson(new DashboardResponse(book), resp.getWriter());
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            resp.setStatus(401);
            gson.toJson(new ExceptionResponse(401, e), resp.getWriter());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            gson.toJson(new ExceptionResponse(500, e), resp.getWriter());
        }
        System.out.println("Out DashboardServlet DELETE");
    }

    private void verifyAdmin(HttpServletRequest req) throws UnauthorizedException {
        User user = (User)req.getSession().getAttribute("user");
        if(!user.getRole().equals(Role.ADMIN))
            throw new UnauthorizedException();
    }

    private ListHelper getBooks(HttpServletRequest request)
    {
        return (ListHelper) request.getServletContext().getAttribute("books");
    }

    private void setBooks(HttpServletRequest request, ListHelper books) {
        request.getServletContext().setAttribute("books", books);
    }

    private boolean addBook(ListHelper books, Book book) {
        if(books.stream().noneMatch(q -> q.title.equalsIgnoreCase(book.title) && q.author.equalsIgnoreCase(book.author) && q.year == book.year)) {
            books.add(book);
            return true;
        }
        return false;
    }

    private Optional<Book> removeBook(List<Book> books, int book) {
        Optional<Book> output = books.stream().filter(q -> q.id == book).findFirst();
        books.removeIf(q -> q.id == book);
        return output;
    }
}
