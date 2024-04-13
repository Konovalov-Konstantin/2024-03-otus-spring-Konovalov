package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.domain.Student;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис студента ")
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(ioService);
    }

    @Mock
    IOService ioService;

    @DisplayName("должен корректно определять студента с введенными именем и фамилией ")
    @Test
    void shouldCorrectDetermineCurrentStudent() {

        String exectedFirstName = "Vladimir";
        String exectedLastName = "Vladimirov";

        given(ioService.readStringWithPrompt("Please input your first name")).willReturn(exectedFirstName);
        given(ioService.readStringWithPrompt("Please input your last name")).willReturn(exectedLastName);

        Student student = studentService.determineCurrentStudent();

        assertAll(
                () -> assertEquals(exectedFirstName, student.firstName()),
                () -> assertEquals(exectedLastName, student.lastName())
        );
    }
}