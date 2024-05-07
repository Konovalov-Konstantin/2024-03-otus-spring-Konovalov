package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import({JdbcBookRepository.class, JdbcAuthorRepository.class, JdbcGenreRepository.class})
class JdbcBookRepositoryTest {

    @Autowired
    JdbcBookRepository bookRepository;

    @Autowired
    JdbcAuthorRepository authorRepository;

    @Autowired
    JdbcGenreRepository genreRepository;

    private static final int EXPECTED_BOOKS_COUNT = 4;

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        List<Book> books = bookRepository.findAll();
        assertEquals(EXPECTED_BOOKS_COUNT, books.size());
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Author testAuthor = Optional.ofNullable(authorRepository.findById(2L)).get().orElse(null);
        Genre testGenre = Optional.ofNullable(genreRepository.findById(3L)).get().orElse(null);
        Book insertedTestBook = new Book(0L, "NewTestBook", testAuthor, testGenre);
        bookRepository.save(insertedTestBook);
        Book expected_book = bookRepository.findById(5).get();
        assertEquals(expected_book, insertedTestBook);
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        Author testAuthor = Optional.ofNullable(authorRepository.findById(2L)).get().orElse(null);
        Genre testGenre = Optional.ofNullable(genreRepository.findById(3L)).get().orElse(null);
        Book updatedTestBook = new Book(2L, "NewTestBook", testAuthor, testGenre);
        bookRepository.save(updatedTestBook);
        Book expected_book = bookRepository.findById(2L).get();
        assertEquals(updatedTestBook, expected_book);
    }

    @DisplayName("находить книгу по id")
    @Test
    void shouldFindBookById() {
        Author expectedAuthor = authorRepository.findById(3).get();
        Genre expectedGenre = genreRepository.findById(3L).get();
        Book book = bookRepository.findById(3L).get();

        assertAll(
                () -> assertEquals("Test_BookTitle_3", book.getTitle()),
                () -> assertEquals(expectedAuthor, book.getAuthor()),
                () -> assertEquals(expectedGenre, book.getGenre())
        );
    }

    @DisplayName("выбрасывать исключение, если ни одной книги не обновлено")
    @Test
    void shouldThrowEntityNotFoundException() {
        Author testAuthor = Optional.ofNullable(authorRepository.findById(2L)).get().orElse(null);
        Genre testGenre = Optional.ofNullable(genreRepository.findById(2L)).get().orElse(null);
        Book missingBook = new Book(10L, "Missing_Book", testAuthor, testGenre);
        assertThrows(EntityNotFoundException.class, () -> bookRepository.save(missingBook));
    }

    @DisplayName("удалять книгу из БД")
    @Test
    void shouldDeleteBook() {
        Book firstBook = bookRepository.findById(1L).get();
        Book deletedBook = bookRepository.findById(2L).get();
        bookRepository.deleteById(2L);
        List<Book> booksAfterDelete = bookRepository.findAll();
        assertFalse(booksAfterDelete.contains(deletedBook));
        assertTrue(booksAfterDelete.contains(firstBook));
    }
}