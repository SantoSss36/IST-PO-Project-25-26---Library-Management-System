package bci;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Book extends Work {

    private List<Creator> _authors = new ArrayList<>();
    private String _isbn;

    public Book(int workId, String title, List<Creator> authors, int price, Category category , String isbn, int copies) {
        super(workId, title, price, category, copies);
        _isbn = isbn;
        _authors = authors;
    }

    public String getIsbn() {
        return _isbn;
    }

    public List<Creator> getAuthorsList() {
        return _authors;
    }

    @Override
    public String getCreatorName() {
        return _authors.stream().map(c -> c.getName()).collect(Collectors.joining("; "));
    }

    @Override
    public List<Creator> getCreators() {
        return _authors;
    }

    @Override
    public String toString() {
        return "" + getWorkId() + " - " + getAvailableCopies() + " de " + getCopies() + " - Livro - " + getTitle() + " - " + getPrice() 
        + " - " + getCategoryString() + " - " + getCreatorName() + " - " + getIsbn();
    }
}