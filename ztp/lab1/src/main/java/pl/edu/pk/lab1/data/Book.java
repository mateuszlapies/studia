package pl.edu.pk.lab1.data;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    private String getJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            return "";
        }
    }

    public String toAdminString() {
        return String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td><input type='button' value='-' onclick='fetch(`/AdminServlet`, {method: `post`, body: `%s`})'/></td></tr>", title, author, year, getJson());
    }
}
