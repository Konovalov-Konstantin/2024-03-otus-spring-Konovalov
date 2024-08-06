package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.models.Book;

@Mapper(componentModel = "spring")
public interface BookToBookDTOMapper {

    BookDTO bookToBookDTO (Book book);

}
