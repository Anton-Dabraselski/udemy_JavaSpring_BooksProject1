package org.udemy.books_pr1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.udemy.books_pr1.models.Book;
import org.udemy.books_pr1.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book showBook(int id){
        return jdbcTemplate.query("SELECT * FROM Book Where book_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }
    public Optional<Book> showBook(String title){
        return jdbcTemplate.query("SELECT * FROM Book Where title=?", new Object[]{title},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public void saveBook(Book book){
        jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES (?,?,?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void updateBook(int id, Book updatedBook){
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE book_id=?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear());
    }

    public void deleteBook(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.user_id = Person.user_id " +
                        "WHERE Book.user_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    // Освбождает книгу (этот метод вызывается, когда человек возвращает книгу в библиотеку)
    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET user_id=NULL WHERE book_id=?", id);
    }

    // Назначает книгу человеку (этот метод вызывается, когда человек забирает книгу из библиотеки)
    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET user_id=? WHERE book_id=?", selectedPerson.getUserId(), id);
    }
}
