package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcBookRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Map.of("id", id);
        return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                "select b.id, b.title, b.author_id, b.genre_id, auth.fullname, gen.name from books b " +
                        "JOIN authors auth ON b.author_id = auth.id " +
                        "JOIN genres gen ON b.genre_id = gen.id " +
                        "WHERE b.id = :id",
                params, new BookRowMapper()));
    }

    @Override
    public List<Book> findAll() {
        return Optional.ofNullable(namedParameterJdbcOperations.query(
                "select b.id, b.title, b.author_id, b.genre_id, auth.fullname, gen.name from books b " +
                        "JOIN authors auth ON b.author_id = auth.id " +
                        "JOIN genres gen ON b.genre_id = gen.id", new BookRowMapper())).orElse(Collections.emptyList());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("boookId", id);
        namedParameterJdbcOperations.update("delete from books where id = :boookId", params);
    }

    private Book insert(Book book) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        var keyHolder = new GeneratedKeyHolder();

        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());

        namedParameterJdbcOperations.update("insert into books (title, author_id, genre_id) " +
                "values (:title, :authorId, :genreId)", params, keyHolder, new String[]{"id"});

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bookId", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());

        int updatedBooks = namedParameterJdbcOperations.update("UPDATE books SET title = :title, " +
                "author_id = :authorId, genre_id = :genreId WHERE id = :bookId", params);

        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
        if (updatedBooks == 0) {
            throw new EntityNotFoundException("Not a single record in the database has been updated");
        }
        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            long authorId = rs.getLong("author_id");
            long genreId = rs.getLong("genre_id");
            String fullName = rs.getString("fullname");
            String name = rs.getString("name");
            return new Book(id, title, new Author(authorId, fullName), new Genre(genreId, name));
        }
    }
}
