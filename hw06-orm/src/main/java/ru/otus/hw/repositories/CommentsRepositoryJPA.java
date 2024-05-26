package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.Optional;

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
