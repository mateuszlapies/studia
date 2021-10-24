package pl.edu.pk.lab2.beans;

import java.io.Serializable;

public class Book implements Serializable {
    public String title;
    public String author;
    public int year;

    public Book() {}

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    @Override
    public String toString() {
        return String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>", title, author, year);
    }

    public String toAdminString(int index) {
        return String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td><input type='button' value='-' onclick='fetch(`/AdminServlet`, {method: `delete`, body: `%s`}).then(() => location.reload())'/></td></tr>", title, author, year, index);
    }
}
