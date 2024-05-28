package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.Modifying;
import ru.otus.hw.models.Book;

public interface BookRepositoryCustom {

    @Modifying
    Book saveOrUpdate(Book book);
}
