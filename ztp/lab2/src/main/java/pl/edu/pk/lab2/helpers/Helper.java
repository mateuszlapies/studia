package pl.edu.pk.lab2.helpers;

import javax.servlet.http.HttpServletResponse;

public class Helper {
    public static void addResponseAttributes(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
    }
}
