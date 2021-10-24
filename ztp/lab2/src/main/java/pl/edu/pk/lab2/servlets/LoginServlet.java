package pl.edu.pk.lab2.servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        if(login.equals("admin")) {
            request.getRequestDispatcher("AdminLoginServlet").forward(request, response);
        } else {
            request.getRequestDispatcher("UserLoginServlet").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().setAttribute("user", "");
        resp.sendRedirect("/");
    }
}
