package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(JdbcAuthorRepository.class)
class JdbcAuthorRepositoryTest {

    private static final int EXPECTED_AUTHOR_COUNT = 4;

    @Autowired
    JdbcAuthorRepository authorRepository;

    @DisplayName("возвращать ожидаемое количество авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        List<Author> allAuthors = authorRepository.findAll();
        assertEquals(EXPECTED_AUTHOR_COUNT, allAuthors.size());
    }

    @DisplayName("возвращать автора по ID")
    @Test
    void shouldReturnAuthorById() {
        Author actualAuthor = new Author(2L, "Test_Author_2");
        Author expectedAuthor = authorRepository.findById(2).orElse(null);
        assertEquals(expectedAuthor, actualAuthor);
    }
}