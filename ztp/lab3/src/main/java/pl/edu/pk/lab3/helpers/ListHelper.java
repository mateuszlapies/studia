package pl.edu.pk.lab3.helpers;

import pl.edu.pk.lab3.beans.Book;

import java.util.ArrayList;

public class ListHelper extends ArrayList<Book> {
    @Override
    public boolean add(Book book) {
        book.id = nextId();
        return super.add(book);
    }

    @Override
    public boolean contains(Object o) {
        return this.stream().anyMatch(q -> q.id == (int)o);
    }

    public int nextId() {
        if(this.size() > 0) {
            this.sort(Book::compareTo);
            for(int i = 0; i < this.size(); i++)
                if(this.get(i).id != i)
                    return i;
            return this.size();
        }
        return 0;
    }
}
