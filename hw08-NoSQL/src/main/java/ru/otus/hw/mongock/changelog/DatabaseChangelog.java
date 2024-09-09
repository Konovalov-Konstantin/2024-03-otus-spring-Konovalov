package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentsRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    private Author author1;

    private Author author2;

    private Author author3;

    private Genre genre1;

    private Genre genre2;

    private Genre genre3;

    private Comment comment1;

    private Comment comment2;

    private Comment comment3;

    private Comment comment4;

    @ChangeSet(order = "001", id = "insertGenres", author = "konovalov")
    public void insertGenres(GenreRepository genreRepository) {
        genre1 = genreRepository.save(new Genre("1", "Роман"));
        genre2 = genreRepository.save(new Genre("2", "Повесть"));
        genre3 = genreRepository.save(new Genre("3", "Публицистика"));
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "konovalov")
    public void insertAuthors(AuthorRepository authorRepository) {
        author1 = authorRepository.save(new Author("1", "Достоевский Федор Михайлович"));
        author2 = authorRepository.save(new Author("2", "Толстой Лев Николаевич"));
        author3 = authorRepository.save(new Author("3", "Солженицын Александр Исаевич"));
    }

    @ChangeSet(order = "003", id = "insertComments", author = "konovalov")
    public void insertComments(CommentsRepository commentsRepository) {
        comment1 = commentsRepository.save(new Comment("1", "Комментарий_1_к_книге_1"));
        comment2 = commentsRepository.save(new Comment("2", "Комментарий_2_к_книге_1"));
        comment3 = commentsRepository.save(new Comment("3", "Комментарий_1_к_книге_2"));
        comment4 = commentsRepository.save(new Comment("4", "Комментарий_1_к_книге_3"));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "konovalov")
    public void insertBooks(BookRepository bookRepository) {
        bookRepository.save(new Book("1", "Преступление и наказание", author1, genre1, List.of(comment1, comment2)));
        bookRepository.save(new Book("2", "Смерть Ивана Ильича", author2, genre2, List.of(comment3)));
        bookRepository.save(new Book("3", "Орбитальный путь", author3, genre3, List.of(comment4)));
    }
}
