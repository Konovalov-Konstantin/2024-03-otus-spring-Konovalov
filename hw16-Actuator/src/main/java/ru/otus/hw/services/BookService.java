package ru.otus.hw.services;

import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;

public interface BookService {
    BookDTO findById(long id);

    List<BookDTO> findAll();

    Book insert(String title, long authorId, long genreId, List<Comment> comments);

    Book update(long id, String title, long authorId, long genreId, List<Comment> comments);

    void deleteById(long id);
}
