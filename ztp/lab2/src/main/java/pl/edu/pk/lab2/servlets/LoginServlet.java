package pl.edu.pk.lab2.servlets;

import com.google.gson.Gson;
import pl.edu.pk.lab2.beans.Role;
import pl.edu.pk.lab2.beans.User;
import pl.edu.pk.lab2.helpers.Helper;
import pl.edu.pk.lab2.exceptions.LoginException;
import pl.edu.pk.lab2.requests.LoginRequest;
import pl.edu.pk.lab2.responses.ExceptionResponse;
import pl.edu.pk.lab2.responses.LoginResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Helper.addResponseAttributes(resp);
        Gson gson = new Gson();
        try {
            LoginRequest request = gson.fromJson(req.getReader(), LoginRequest.class);
            User user;
            if(request.isAdmin())
                user = adminLogin(request);
            else
                user = userLogin(request);
            login(user, req.getSession(true), resp);
            resp.setStatus(200);
            gson.toJson(new LoginResponse(), resp.getWriter());
        } catch (LoginException e) {
            e.printStackTrace();
            resp.setStatus(400);
            gson.toJson(new ExceptionResponse(400, e));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            gson.toJson(new ExceptionResponse(500, e));
        }
    }

    private User createUser(LoginRequest request, Role role) {
        return new User(request, role);
    }

    private User userLogin(LoginRequest request) throws LoginException {
        if("user".equals(request.login) && "user".equals(request.pass) || "login".equals(request.login) && "login".equals(request.pass))
            return createUser(request, Role.USER);
        else
            throw new LoginException();
    }

    private User adminLogin(LoginRequest request) {
        return createUser(request, Role.ADMIN);
    }

    private void login(User user, HttpSession session, HttpServletResponse response) {
        session.setAttribute("user", user);
        response.addCookie(new Cookie("userId", user.getUserId()));
    }
}
