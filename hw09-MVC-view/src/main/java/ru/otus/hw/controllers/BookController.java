package ru.otus.hw.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        model.addAttribute("book", new BookDTO());
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("comments", comments);
        return "new";
    }

    @PostMapping("/books")
    public String create(@ModelAttribute("book") BookDTO book) {
        String title = book.getTitle();
        long authorId = book.getAuthor().getId();
        long genreId = book.getGenre().getId();
        List<Comment> comments = book.getComments();
        bookService.insert(title, authorId, genreId, comments);
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

    @PatchMapping("/edit")
    public String saveBook(@ModelAttribute("book") BookDTO book) {
        long bookId = book.getId();
        Long authorId = Optional.ofNullable(book.getAuthor()).map(Author::getId).orElse(null);
        Long genreId = Optional.ofNullable(book.getGenre()).map(Genre::getId).orElse(null);
        String title = Optional.ofNullable(book.getTitle()).orElse(null);
        List<Comment> comments = Optional.ofNullable(book.getComments()).orElse(Collections.emptyList());
        bookService.update(bookId, title, authorId, genreId, comments);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
