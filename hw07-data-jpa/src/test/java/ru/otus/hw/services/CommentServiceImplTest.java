package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Сервис коммментариев должен ")
@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    CommentServiceImpl commentService;
    @MockBean
    BookRepository bookRepository;

    @DisplayName("возвращать все комментарии книги по ID-книги")
    @Test
    void findCommentsByBookId() {
        Book expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setComments(List.of(new Comment(1L, "Comment_1"),
                new Comment(2L, "Comment_2")));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(expectedBook));

        List<Comment> commentsByBookId = commentService.findCommentsByBookId(1L);

        assertIterableEquals(commentsByBookId, expectedBook.getComments());

    }
}