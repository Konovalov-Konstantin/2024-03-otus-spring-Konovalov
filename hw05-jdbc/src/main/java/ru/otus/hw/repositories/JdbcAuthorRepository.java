package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcAuthorRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Author> findAll() {
        return Optional.ofNullable(namedParameterJdbcOperations.query("select id, fullName from authors",
                new AuthorRowMapper())).orElse(Collections.emptyList());
    }

    @Override
    public Optional<Author> findById(long id) {
        Map<String, Object> params = Map.of("id", id);
        return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                "select id, fullName from authors where id = :id", params, new AuthorRowMapper()));
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String fullName = rs.getString("fullName");
            return new Author(id, fullName);
        }
    }
}
