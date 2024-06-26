package ru.otus.hw.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Data
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    @GetMapping("/")
    public String getAllBooks(Model model) {
        List<BookDTO> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/books/{id}")
    public String getPerson(@PathVariable("id") long id, Model model) {
        BookDTO book = bookService.findById(id);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/create")
    public String createBook(Model model) {
        List<Author> authors = authorService.findAll();
        List<Genre> genres = genreService.findAll();
        List<Comment> comments = new ArrayList<>();
        BookDTO newBook = new BookDTO();
        model.addAttribute("book", newBook);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("comments", comments);
        return "edit";
    }

    @PostMapping("/books")
    public String create(@ModelAttribute("book") BookDTO book) {
        String title = book.getTitle();
        long bookId = book.getId();
        long authorId = book.getAuthor().getId();
        long genreId = book.getGenre().getId();
        List<Comment> comments = book.getComments();
        if (bookId == 0L) {
            bookService.insert(title, authorId, genreId, comments);
        } else {
            bookService.update(bookId, title, authorId, genreId, comments);
        }
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editBookPage(@RequestParam("id") long id, Model model) {
        BookDTO book = bookService.findById(id);
        List<Author> authors = authorService.findAll();
        List<Genre> genres = genreService.findAll();
        List<Comment> comments = commentService.findCommentsByBookId(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("comments", comments);
        return "edit";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
