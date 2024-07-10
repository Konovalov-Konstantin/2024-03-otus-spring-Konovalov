package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @WithMockUser
    public void testAuthenticatedUserMainPage() throws Exception {
        mockMvc.perform(get("/main"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testNotAuthenticatedUserMainPage() throws Exception {
        mockMvc.perform(get("/main"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    public void testAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testNotAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testAuthenticatedUserCreatePage() throws Exception {
        mockMvc.perform(get("/create"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testNotAuthenticatedUserCreatePage() throws Exception {
        mockMvc.perform(get("/create"))
                .andExpect(status().is3xxRedirection());
    }
}