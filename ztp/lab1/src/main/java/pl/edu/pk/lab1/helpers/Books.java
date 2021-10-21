package pl.edu.pk.lab1.helpers;

import pl.edu.pk.lab1.data.Book;

import java.io.PrintWriter;
import java.util.List;

public class Books {
    public static void Print(PrintWriter writer, List<Book> books, boolean admin) {
        writer.write("<table style='text-align: center'><tr><th>Title</th><th>Author</th><th>Year</th>");
        if(admin)
            writer.write("<th>Delete</th>");
        writer.write("</tr>");
        for(int i = 0; i < books.size(); i++) {
            if(admin)
                writer.write(books.get(i).toAdminString(i));
            else
                writer.write(books.get(i).toString());
        }
        writer.write("</table>");
    }

    public static void Add(PrintWriter writer) {
        writer.write("<form method='post' action='/AdminServlet'>");
        writer.write("<table style='text-align: center; padding-top: 1rem'>");
        writer.write("<tr>");
        writer.write("<td><input type='text' name='title'/></td>");
        writer.write("<td><input type='text' name='author'/></td>");
        writer.write("<td><input type='number' name='year'/></td>");
        writer.write("<td><input type='submit' value='+'/></td>");
        writer.write("</tr>");
        writer.write("</table></form>");
    }
}
