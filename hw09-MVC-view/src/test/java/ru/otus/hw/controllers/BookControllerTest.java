package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private CommentService commentService;

    @Test
    void shouldReturnCorrectBooksList() throws Exception {

        List<BookDTO> expectedBooks = List.of(new BookDTO(1, "Test_BookTitle_1",
                new Author(1, "Test_Author_1"),
                new Genre(1, "Test_Genre_1"),
                List.of(new Comment(1, "Comment_1_by_book_1"), new Comment(2, "Comment_2_by_book_1"))));

        given(bookService.findAll()).willReturn(expectedBooks);

        mvc.perform(get("/")
                        .content(mapper.writeValueAsString(expectedBooks))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test_BookTitle_1")))
                .andExpect(content().string(containsString("Test_Author_1")))
                .andExpect(content().string(containsString("Comment_1_by_book_1")))
                .andExpect(content().string(containsString("Comment_2_by_book_1")));
    }

    @Test
    void shouldReturnBookById() throws Exception {
        BookDTO bookDTO = new BookDTO(1, "Test_BookTitle_1",
                new Author(1, "Test_Author_1"),
                new Genre(1, "Test_Genre_1"),
                List.of(new Comment(1, "Comment_1_by_book_1")));

        given(bookService.findById(1)).willReturn(bookDTO);

        mvc.perform(get("/books/1")
                        .content(mapper.writeValueAsString(bookDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test_BookTitle_1")))
                .andExpect(content().string(containsString("Test_Author_1")))
                .andExpect(content().string(containsString("Comment_1_by_book_1")))
                .andExpect(content().string(containsString("На главную")));
    }

    @Test
    void shouldCorrectDeleteBook() throws Exception {
        mvc.perform(delete("/1"));

        verify(bookService, times(1)).deleteById(1L);
    }
}