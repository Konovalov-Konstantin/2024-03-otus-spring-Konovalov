package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с авторами должно")
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
                () -> assertEquals("Комментарий_2_к_книге_1", expectedCommentary.getComment())
        );
    }

    @DisplayName("возвращать все комментарии книги по ID-книги")
    @Test
    void shouldReturnCommentByBookId() {
        List<Comment> commentsByBookId = commentsRepository.findCommentsByBookId(1L);
        assertAll(
                () -> assertEquals(2, commentsByBookId.size()),
                () -> assertEquals("Комментарий_1_к_книге_1", commentsByBookId.get(0).getComment()),
                () -> assertEquals("Комментарий_2_к_книге_1", commentsByBookId.get(1).getComment())
        );
    }
}