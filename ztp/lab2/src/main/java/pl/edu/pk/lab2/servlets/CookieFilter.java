package pl.edu.pk.lab2.servlets;

import com.google.gson.Gson;
import pl.edu.pk.lab2.beans.User;
import pl.edu.pk.lab2.exceptions.NoValidCookieException;
import pl.edu.pk.lab2.exceptions.UnauthorizedException;
import pl.edu.pk.lab2.helpers.Helper;
import pl.edu.pk.lab2.responses.ExceptionResponse;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/DashboardServlet", "/DashboardServlet*"})
public class CookieFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        try {
            Object userObj = req.getSession().getAttribute("user");
            if(userObj == null) {
                throw new UnauthorizedException();
            }
            User user = (User) userObj;
            System.out.println(user);
            if(!checkForUserIdCookie(req.getCookies(), user)) {
                throw new NoValidCookieException();
            }
            chain.doFilter(request, response);
        } catch (Exception ex) {
            Gson gson = new Gson();
            HttpServletResponse resp = (HttpServletResponse) response;
            Helper.addResponseAttributes(resp);
            resp.setStatus(401);
            gson.toJson(new ExceptionResponse(401, ex), response.getWriter());
        }
    }

    private boolean checkForUserIdCookie(Cookie[] cookies, User user) {
        for (Cookie cookie : cookies) {
            if ("userId".equals(cookie.getName())) {
                return new String(Base64.getDecoder()
                        .decode(cookie.getValue().getBytes()))
                        .equals(user.getLogin());
            }
        }
        return false;
    }
}
