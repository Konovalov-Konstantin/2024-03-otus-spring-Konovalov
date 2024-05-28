package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Comment;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с комментариями должно")
@DataJpaTest
@Import(CommentsRepositoryJPA.class)
class CommentsRepositoryJPATest {

    @Autowired
    CommentsRepositoryJPA commentsRepository;

    @DisplayName("возвращать комментарий по ID")
    @Test
    void shouldReturnCommentById() {
        Comment expectedCommentary = commentsRepository.findById(2).orElse(null);
        assertAll(
                () -> assertNotNull(expectedCommentary),
                () -> assertEquals("Comment_2_by_book_1", expectedCommentary.getComment())
        );
    }
}