package org.udemy.books_pr1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.udemy.books_pr1.dao.BookDAO;
import org.udemy.books_pr1.dao.PersonDAO;
import org.udemy.books_pr1.models.Book;
import org.udemy.books_pr1.models.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;



    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
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

    @PostMapping()
    public String createBook(@ModelAttribute("book")Book book){
        bookDAO.saveBook(book);
        return "redirect:/books";
    }
    @GetMapping("/{id_book}/edit")
    public String editBook(Model model, @PathVariable("id_book") int id){
        model.addAttribute("book", personDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") Book book, BindingResult bindingResult, @PathVariable("id_book") int id){
        if(bindingResult.hasErrors()) return "books/edit";

        bookDAO.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id_book}")
    public String deleteBook(@PathVariable("id_book") int id){
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }
}
