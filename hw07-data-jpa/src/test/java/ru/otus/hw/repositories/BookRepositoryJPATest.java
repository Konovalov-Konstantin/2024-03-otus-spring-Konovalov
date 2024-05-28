package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с книгами должно")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookRepositoryJPATest {

    @Autowired
    BookRepository bookRepository;

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Author testAuthor = new Author(2L, "Test_Author_2");
        Genre testGenre = new Genre(3L, "Test_Genre_3");
        Book insertedTestBook = new Book(0L, "NewTestBook", testAuthor, testGenre, Collections.emptyList());
        bookRepository.save(insertedTestBook);
        Book expected_book = bookRepository.findById(5L).orElse(null);
        assertEquals(expected_book, insertedTestBook);
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        Author testAuthor = new Author(2L, "Test_Author_2");
        Genre testGenre = new Genre(3L, "Test_Genre_3");
        Comment testComment = new Comment(4, "Измененный_комментарий_1_к_книге_2");
        Book updatedTestBook = new Book(2L, "NewTestBook", testAuthor, testGenre, List.of(testComment));
        bookRepository.save(updatedTestBook);
        Book expected_book = bookRepository.findById(2L).orElse(null);
        assertAll(
                () -> {
                    assert expected_book != null;
                    assertIterableEquals(expected_book.getComments(), updatedTestBook.getComments());
                },
                () -> assertEquals(updatedTestBook, expected_book)
        );
    }

    @DisplayName("выбрасывать исключение, если ни одной книги не обновлено")
    @Test
    void shouldThrowEntityNotFoundException() {
        Author testAuthor = new Author(2L, "Test_Author_2");
        Genre testGenre = new Genre(2L, "Test_Genre_2");
        Book missingBook = new Book(10L, "Missing_Book", testAuthor, testGenre, Collections.emptyList());
        assertThrows(EntityNotFoundException.class, () -> bookRepository.saveOrUpdate(missingBook));
    }

    @DisplayName("удалять книгу из БД")
    @Test
    void shouldDeleteBook() {
        Book firstBook = new Book(1L, "Test_BookTitle_1", new Author(1L, "Test_Author_1"),
                new Genre(1L, "Test_Genre_1"),
                List.of(new Comment(1L, "Comment_1_by_book_1"), new Comment(2L, "Comment_2_by_book_1")));

        Book deletedBook = new Book(2L, "Test_BookTitle_2", new Author(2L, "Test_Author_2"),
                new Genre(2L, "Test_Genre_2"),
                List.of(new Comment(3L, "Comment_1_by_book_2")));

        bookRepository.deleteById(2L);
        List<Book> booksAfterDelete = bookRepository.findAll();
        assertFalse(booksAfterDelete.contains(deletedBook));
        assertTrue(booksAfterDelete.contains(firstBook));
    }
}