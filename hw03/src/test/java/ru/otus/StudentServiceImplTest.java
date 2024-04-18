package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Student;
import ru.otus.service.IOService;
import ru.otus.service.StudentServiceImpl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис студента ")
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    IOService ioService;
    @Mock
    QuestionDao questionDao;

    @DisplayName("должен корректно определять студента с введенными именем и фамилией ")
    @Test
    void shouldCorrectDetermineCurrentStudent() {

        String expectedFirstName = "Vladimir";
        String expectedLastName = "Vladimirov";

        given(questionDao.getMessage("firstName")).willReturn("Please input your first name");
        given(questionDao.getMessage("lastName")).willReturn("Please input your last name");
        given(ioService.readStringWithPrompt("Please input your first name")).willReturn(expectedFirstName);
        given(ioService.readStringWithPrompt("Please input your last name")).willReturn(expectedLastName);

        Student student = studentService.determineCurrentStudent();

        assertAll(
                () -> assertEquals(expectedFirstName, student.firstName()),
                () -> assertEquals(expectedLastName, student.lastName())
        );
    }
}