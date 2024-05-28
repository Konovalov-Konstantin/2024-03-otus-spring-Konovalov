package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional
    public Book saveOrUpdate(Book book) {

        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            Optional<Book> updatedBook = Optional.ofNullable(em.find(Book.class, book.getId()));
            if (updatedBook.isPresent()) {
                List<Comment> commentsFromUpdatedBook = updatedBook.map(Book::getComments)
                        .orElse(Collections.emptyList());
                List<Comment> newComments = book.getComments();
                commentsFromUpdatedBook.addAll(newComments);
                book.setComments(commentsFromUpdatedBook);
                em.merge(book);
                return book;
            } else {
                throw new EntityNotFoundException("Not a single record in the database has been updated");
            }
        }
    }
}
