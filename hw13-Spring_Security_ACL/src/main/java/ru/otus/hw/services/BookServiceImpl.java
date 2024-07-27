package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.BookToBookDTOMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookToBookDTOMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public BookDTO findById(long id) {
        BookDTO book = bookRepository.findById(id).map(mapper::bookToBookDTO).orElse(null);
        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findAll() {
        List<BookDTO> books = bookRepository.findAll().stream().map(mapper::bookToBookDTO).toList();
        return books;
    }

    @Override
    @Transactional
    public Book insert(String title, long authorId, long genreId, List<Comment> comments) {
        return save(0, title, authorId, genreId, comments);
    }

    @Override
    @Transactional
    public Book update(long id, String title, long authorId, long genreId, List<Comment> comments) {
        return save(id, title, authorId, genreId, comments);
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Book save(long id, String title, long authorId, long genreId, List<Comment> comments) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));
        var book = new Book(id, title, author, genre, comments);
        return bookRepository.save(book);
    }
}
