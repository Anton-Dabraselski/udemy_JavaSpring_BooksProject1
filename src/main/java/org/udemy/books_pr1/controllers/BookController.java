package org.udemy.books_pr1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.udemy.books_pr1.dao.BookDAO;
import org.udemy.books_pr1.dao.PersonDAO;
import org.udemy.books_pr1.models.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookController bookController;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookController bookController) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookController = bookController;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{book_id}")
    public String showBook(@PathVariable("book_id") int id, Model model, @ModelAttribute("person")Person person){
        //Get 1 book from DAO
        model.addAttribute("book", bookDAO.showBook(id));

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if(bookOwner.isPresent()){
            model.addAttribute("owner", bookOwner.get());
        }else {
            model.addAttribute("people", personDAO.index());
        }

        return "/books/showBook";
    }
}
