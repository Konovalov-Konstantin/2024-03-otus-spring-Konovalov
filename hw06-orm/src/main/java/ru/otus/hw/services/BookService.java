package ru.otus.hw.services;

import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> findById(long id);

    List<Book> findAll();

    Book insert(String title, long authorId, long genreId, List<Comment> comments);

    Book update(long id, String title, long authorId, long genreId, List<Comment> comments);

    void deleteById(long id);
}
