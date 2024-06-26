package ru.otus.hw.repositories;

import ru.otus.hw.models.Comment;

import java.util.Optional;

public interface CommentsRepository {

    Optional<Comment> findById(long id);

    Comment save(Comment comment);

    void deleteById(long id);
}
