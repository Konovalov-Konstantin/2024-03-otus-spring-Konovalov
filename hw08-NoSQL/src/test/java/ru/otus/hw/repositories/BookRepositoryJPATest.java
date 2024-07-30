package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с книгами должно")
@DataMongoTest
@EnableConfigurationProperties
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
class BookRepositoryJPATest {

    @Autowired
    BookRepository bookRepository;

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Author testAuthor = new Author("2", "Test_Author_2");
        Genre testGenre = new Genre("3", "Test_Genre_3");
        Book insertedTestBook = new Book("5", "NewTestBook", testAuthor, testGenre, Collections.emptyList());
        Book save = bookRepository.save(insertedTestBook);
        Book expected_book = bookRepository.findById(save.getId()).orElse(null);
        assertEquals(expected_book, insertedTestBook);
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        Author testAuthor = new Author("2", "Test_Author_2");
        Genre testGenre = new Genre("3", "Test_Genre_3");
        Comment testComment = new Comment("4", "Измененный_комментарий_1_к_книге_2");
        Book updatedTestBook = new Book("2", "NewTestBook", testAuthor, testGenre, List.of(testComment));
        bookRepository.save(updatedTestBook);
        Book expected_book = bookRepository.findById("2").orElse(null);
        assertAll(
                () -> {
                    assert expected_book != null;
                    assertIterableEquals(expected_book.getComments(), updatedTestBook.getComments());
                },
                () -> assertEquals(updatedTestBook, expected_book)
        );
    }

    @DisplayName("удалять книгу из БД")
    @Test
    void shouldDeleteBook() {

        Book firstBook = new Book("1", "Test_BookTitle_1", new Author("1", "Test_Author_1"),
                new Genre("1", "Test_Genre_1"),
                List.of(new Comment("1", "Comment_1_by_book_1"), new Comment("2", "Comment_2_by_book_1")));

        Book secondBook = new Book("2", "Test_BookTitle_2", new Author("2", "Test_Author_2"),
                new Genre("2", "Test_Genre_2"),
                List.of(new Comment("3", "Comment_1_by_book_2")));

        bookRepository.deleteAll();
        bookRepository.save(firstBook);
        bookRepository.save(secondBook);

        bookRepository.deleteById("2");
        List<Book> booksAfterDelete = bookRepository.findAll();
        assertFalse(booksAfterDelete.contains(secondBook));
        assertTrue(booksAfterDelete.contains(firstBook));
    }
}