package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
class BookControllerTest {

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
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    @DisplayName("Доступ на главную страницу есть у всех аутентифицированных пользователей")
    public void testAuthenticatedUserMainPage() throws Exception {
        mockMvc.perform(get("/main"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Доступ на главную страницу отсутствует у неаутентифицированных пользователей")
    public void testNotAuthenticatedUserMainPage() throws Exception {
        mockMvc.perform(get("/main"))
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Авторизованный пользователь может создавать книгу")
    public void testAuthorizedUserCreateBook() throws Exception {
        mockMvc.perform(get("/create"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    @DisplayName("Неавторизованный пользователь не может создавать книгу")
    public void testUnAuthorizededUserCreateBook() throws Exception {
        mockMvc.perform(get("/create"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Авторизованный пользователь может удалить книгу")
    public void testAuthorizedUserDeleteBook() throws Exception {
        mockMvc.perform(post("/delete/{id}", 1L))
                .andExpect(redirectedUrl("/main"));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    @DisplayName("Неавторизованный пользователь не может удалить книгу")
    public void testUnAuthorizedUserDeleteBook() throws Exception {
        mockMvc.perform(post("/delete/{id}", 1L))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Авторизованный пользователь может изменять книгу")
    public void testAuthorizedUserEditBook() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);

        when(bookService.findById(1L)).thenReturn(bookDTO);
        mockMvc.perform(get("/edit").param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    @DisplayName("Неавторизованный пользователь не может изменять книгу")
    public void testNotAuthorizedUserEditBook() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);

        when(bookService.findById(1L)).thenReturn(bookDTO);
        mockMvc.perform(get("/edit").param("id", "1"))
                .andExpect(status().is4xxClientError());
    }
}