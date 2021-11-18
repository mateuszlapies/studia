package pl.edu.pk.lab3.beans;

import java.io.Serializable;

public class Book implements Comparable<Book> {
    public int id;
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
    public int compareTo(Book o) {
        return Integer.compare(id, o.id);
    }
}
