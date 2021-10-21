package pl.edu.pk.lab1.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "WelcomeServlet", value = "/WelcomeServlet")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) getServletContext().getAttribute("user");
        if(user == null || user.isEmpty())
            response.sendRedirect("/index.html");
        else
            if("admin".equals(user))
                request.getRequestDispatcher("/AdminServlet").forward(request, response);
            else
                request.getRequestDispatcher("/DashboardServlet").forward(request, response);
    }
}
