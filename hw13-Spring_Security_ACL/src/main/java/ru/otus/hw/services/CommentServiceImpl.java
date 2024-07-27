package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentsRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentsRepository commentsRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> findById(long id) {
        return commentsRepository.findById(id);
    }

    @Override
    public Comment insert(String comment) {
        return save(0, comment);
    }

    @Override
    public Comment update(long id, String comment) {
        return save(id, comment);
    }

    @Override
    public void deleteById(long id) {
        commentsRepository.deleteById(id);
    }

    @Override
    public List<Comment> findCommentsByBookId(long bookId) {
        List<Comment> comments = bookRepository.findById(bookId)
                .map(Book::getComments).orElse(Collections.emptyList());
        return comments;
    }

    private Comment save(long id, String comment) {
        Comment newComment = new Comment(id, comment);
        return commentsRepository.save(newComment);
    }
}
