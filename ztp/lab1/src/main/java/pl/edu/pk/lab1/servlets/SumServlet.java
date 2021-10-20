package pl.edu.pk.lab1.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SumServlet", value = "/SumServlet")
public class SumServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameterA = req.getParameter("a");
        String parameterB = req.getParameter("b");
        try {
            int valueA = Integer.parseInt(parameterA);
            int valueB = Integer.parseInt(parameterB);
            resp.getWriter().write(String.format("%s + %s = %s", valueA, valueB, valueA + valueB));
            resp.getWriter().flush();
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
