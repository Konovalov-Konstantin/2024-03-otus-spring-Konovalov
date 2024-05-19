package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> findById(long id);
    Comment insert(long id, String comment);
    Comment update(long id, String comment);
    void deleteById(long id);
    List<Comment> findCommentsByBookId(long bookId);
}
