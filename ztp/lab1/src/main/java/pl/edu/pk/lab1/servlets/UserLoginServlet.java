package pl.edu.pk.lab1.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "UserLoginServlet", value = "/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
    private HashMap<String, String> logins;

    @Override
    public void init() throws ServletException {
        logins = new HashMap<>();
        logins.put("user", "user");
        logins.put("login", "login");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("login");
        String pass = request.getParameter("pass");
        if(checkUser(user, pass))
            request.getRequestDispatcher("DashboardServlet").forward(request, response);
        else
            response.sendRedirect("loginFailed.html");
    }

    private boolean checkUser(String user, String pass) {
        String p = logins.getOrDefault(user, null);
        return p != null && p.equals(pass);
    }
}
