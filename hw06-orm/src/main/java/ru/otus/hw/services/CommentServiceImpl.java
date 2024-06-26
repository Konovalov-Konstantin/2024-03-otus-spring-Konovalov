package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentsRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentsRepository commentsRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> findById(long id) {
        return commentsRepository.findById(id);
    }

    @Override
    @Transactional
    public Comment insert(long id, String comment) {
        return save(id, comment);
    }

    @Override
    @Transactional
    public Comment update(long id, String comment) {
        return save(id, comment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentsRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findCommentsByBookId(long bookId) {
        List<Comment> comments = bookRepository.findById(bookId)
                .map(book -> book.getComments()).orElse(Collections.emptyList());
        return comments;
    }

    private Comment save(long id, String comment) {
        Comment newComment = new Comment(id, comment);
        return commentsRepository.save(newComment);
    }
}
