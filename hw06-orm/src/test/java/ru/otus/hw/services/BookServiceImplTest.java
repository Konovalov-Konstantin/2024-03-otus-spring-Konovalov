package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис книг должен ")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BookServiceImplTest {

    @Autowired
    BookServiceImpl bookService;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    GenreRepository genreRepository;

    @DisplayName("должен возвращать книгу по ID ")
    @Test
    void shouldReturnBookById() {
        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setAuthor(new Author(1, "Test_Author_1"));
        expectedBook.setTitle("Test_BookTitle_1");
        expectedBook.setGenre(new Genre(1l, "Test_Genre_1"));
        expectedBook.setComments(List.of(new Comment(1, "Комментарий_1_к_книге_1"), new Comment(2, "Комментарий_2_к_книге_1")));

        Book actualBook = bookService.findById(1l).orElse(null);
        assertEquals(actualBook, expectedBook);
    }

    @DisplayName("должен возвращать все книги из БД ")
    @Test
    void shouldReturnAllBooks() {
        List<Book> books = bookService.findAll();
        assertEquals(4, books.size());
    }

    @DisplayName("должен сохранять новую книгу в БД ")
    @Test
    void shouldSaveNewBook() {
        Book newBook = new Book();
        newBook.setAuthor(authorRepository.findById(3L).orElse(null));
        newBook.setGenre(genreRepository.findById(2L).orElse(null));
        newBook.setTitle("Test_BookTitle_5");
        newBook.setComments(List.of(new Comment(5L, "Комментарий_1_к_книге_5")));

        long insertedBookId = bookService.insert(newBook.getTitle(), newBook.getAuthor().getId(),
                newBook.getGenre().getId(), newBook.getComments()).getId();

        Book insertedBook = bookService.findById(insertedBookId).orElse(null);

        assertAll(
                () -> assertEquals(insertedBook.getGenre(), newBook.getGenre()),
                () -> assertEquals(1, insertedBook.getComments().size()),
                () -> assertEquals(insertedBook.getComments().get(0), newBook.getComments().get(0)),
                () -> assertEquals(insertedBook.getTitle(), newBook.getTitle()),
                () -> assertEquals(insertedBook.getAuthor(), newBook.getAuthor())
        );
    }

    @DisplayName("должен обновлять книгу в БД ")
    @Test
    void shouldUpdateBook() {
        Optional<Book> bookBeforeUpdate = bookService.findById(1L);
        assertEquals(2, bookBeforeUpdate.get().getComments().size());
        List<Comment> comments = List.of(new Comment(5L, "new Comment"));
        bookService.update(1L, "UpdatedTitile", 3L, 2L, comments);

        Optional<Book> bookAfterUpdate = bookService.findById(1L);
        assertAll(
                () -> assertEquals(3, bookAfterUpdate.get().getComments().size()),
                () -> assertEquals("UpdatedTitile", bookAfterUpdate.get().getTitle())
        );
    }
}