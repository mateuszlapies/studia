package pl.edu.pk.lab1.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.edu.pk.lab1.data.Book;

import java.io.PrintWriter;
import java.util.List;

public class Books {
    public static void Print(PrintWriter writer, List<Book> books, boolean admin) {
        writer.write("<table style='text-align: center'><tr><th>Title</th><th>Author</th><th>Year</th>");
        if(admin)
            writer.write("<th>Delete</th>");
        writer.write("</tr>");
        for(Book book : books) {
            if(admin)
                writer.write(book.toAdminString());
            else
                writer.write(book.toString());
        }
        writer.write("</table>");
    }

    public static void Add(PrintWriter writer) {
        writer.write("<form onsubmit='fetch(`/AdminServlet`, {method: `post`, body: JSON.stringify({title: event.title.value, author: event.author.value, event.year.value})})'>");
        writer.write("<table>");
        writer.write("<tr>");
        writer.write("<td><input type='text' name='title'/></td>");
        writer.write("<td><input type='text' name='author'/></td>");
        writer.write("<td><input type='number' name='year'/></td>");
        writer.write("<td><input type='submit' value='+'/></td>");
        writer.write("</tr>");
        writer.write("</table>");
    }
}
