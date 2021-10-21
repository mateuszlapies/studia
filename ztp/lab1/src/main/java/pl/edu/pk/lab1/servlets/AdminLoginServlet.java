package pl.edu.pk.lab1.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AdminLoginServlet", value = "/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pass = request.getParameter("pass");
        if("admin".equals(pass)) {
            getServletContext().setAttribute("user", request.getParameter("login"));
            request.getRequestDispatcher("/AdminServlet").forward(request, response);
        } else
            response.sendRedirect("loginFailed.html");
    }
}
