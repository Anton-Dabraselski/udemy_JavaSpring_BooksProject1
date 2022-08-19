package org.udemy.books_pr1.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Book {
    private int book_id;

    @NotEmpty(message = "Title should not be Empty")
    private String title;
    @NotEmpty(message = "Author should not be Empty")
    private String author;
    @NotEmpty(message = "Year should not be Empty")
    private int year;
    private int user_id;

    public Book(){}

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
