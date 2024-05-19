package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class BookRepositoryJPA implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJPA(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("books-authors-genres-comments-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.id = :id", Book.class);
        query.setParameter("id", id);
        query.setHint(FETCH.getKey(), entityGraph);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("books-authors-genres-comments-graph");
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
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
        Optional<Book> removeBook = findById(id);
        removeBook.ifPresent(em::remove);
    }

    private Book insert(Book book) {
        em.persist(book);
        return book;
    }

    private Book update(Book book) {
        try {
            Optional<Book> updatedBook = findById(book.getId());
            if (Objects.nonNull(updatedBook)) {
                List<Comment> commentsFromUpdatedBook = updatedBook.map(Book::getComments)
                        .orElse(Collections.emptyList());
                List<Comment> newComments = book.getComments();
                commentsFromUpdatedBook.addAll(newComments);
                book.setComments(commentsFromUpdatedBook);
                em.merge(book);
                return book;
            }
        } catch (Exception e) {
            throw new EntityNotFoundException("Not a single record in the database has been updated");
        }
        return null;
    }
}

