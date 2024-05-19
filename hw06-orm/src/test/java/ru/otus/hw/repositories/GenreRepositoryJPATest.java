package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с жанрами должно")
@DataJpaTest
@Import(GenreRepositoryJPA.class)
class GenreRepositoryJPATest {

    @Autowired
    GenreRepositoryJPA genreRepository;

    private static final int EXPECTED_GENRE_COUNT = 4;

    @DisplayName("возвращать ожидаемое количество жанров в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        List<Genre> allGenres = genreRepository.findAll();
        assertEquals(EXPECTED_GENRE_COUNT, allGenres.size());
    }

    @DisplayName("возвращать жанр по ID")
    @Test
    void shouldReturnAuthorById() {
        Genre actualGenre = new Genre(3L, "Test_Genre_3");
        Genre expectedGenre = genreRepository.findById(3).orElse(null);
        assertEquals(expectedGenre, actualGenre);
    }
}