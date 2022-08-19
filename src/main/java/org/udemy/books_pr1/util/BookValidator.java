package org.udemy.books_pr1.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.udemy.books_pr1.dao.BookDAO;
import org.udemy.books_pr1.models.Book;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        if(bookDAO.showBook(book.getTitle()).isPresent()){
            errors.rejectValue("title", "This Title is already on list");
        }
    }
}
