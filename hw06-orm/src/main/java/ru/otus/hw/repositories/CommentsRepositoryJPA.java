package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class CommentsRepositoryJPA implements CommentsRepository {

    @PersistenceContext
    private final EntityManager em;

    public CommentsRepositoryJPA(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findCommentsByBookId(long bookId) {
        return Optional.ofNullable(em.find(Book.class, bookId))
                .map(Book::getComments)
                .orElse(Collections.emptyList());
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            return insert(comment);
        }
        return update(comment);
    }

    @Override
    public void deleteById(long id) {
        Optional<Comment> removeComment = findById(id);
        removeComment.ifPresent(em::remove);
    }

    private Comment insert(Comment comment) {
        em.persist(comment);
        return comment;
    }

    private Comment update(Comment comment) {
        em.merge(comment);
        return comment;
    }
}
