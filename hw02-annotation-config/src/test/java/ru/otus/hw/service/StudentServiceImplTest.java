package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.domain.Student;

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

    @DisplayName("должен корректно определять студента с введенными именем и фамилией ")
    @Test
    void shouldCorrectDetermineCurrentStudent() {

        String expectedFirstName = "Vladimir";
        String expectedLastName = "Vladimirov";

        given(ioService.readStringWithPrompt("Please input your first name")).willReturn(expectedFirstName);
        given(ioService.readStringWithPrompt("Please input your last name")).willReturn(expectedLastName);

        Student student = studentService.determineCurrentStudent();

        assertAll(
                () -> assertEquals(expectedFirstName, student.firstName()),
                () -> assertEquals(expectedLastName, student.lastName())
        );
    }
}