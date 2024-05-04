package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.config.TestConfig;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Student;
import ru.otus.domain.TestResult;
import ru.otus.service.IOService;
import ru.otus.service.ResultServiceImpl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Сервис вывода результатов ")
class ResultServiceImplTest {
    @Autowired
    private ResultServiceImpl resultService;
    @MockBean
    IOService ioService;
    @MockBean
    TestConfig testConfig;
    @MockBean
    QuestionDao questionDao;

    @DisplayName("должен корректно выводить результаты тестирования студента ")
    @Test
    void shouldCorrectReturnTestResult() {

        given(testConfig.getRightAnswersCountToPass()).willReturn(4);

        lenient().when(questionDao.getMessage("currentStudent")).thenReturn("Student:");
        lenient().when(questionDao.getMessage("answered")).thenReturn("Answered questions count:");
        lenient().when(questionDao.getMessage("rightAnswers")).thenReturn("Right answers count:");
        lenient().when(questionDao.getMessage("ifFail")).thenReturn("Sorry. You fail test.");
        lenient().when(questionDao.getMessage("results")).thenReturn("Test results:");

        Student student = new Student("name", "surname");
        TestResult testResult = new TestResult(student);

        testResult.setRightAnswersCount(3);

        resultService.showResult(testResult);

        assertAll(
                () -> verify(ioService, times(3)).printLine(anyString()),
                () -> verify(ioService, times(1)).printFormattedLine("Student:" + student.getFullName()),
                () -> verify(ioService, times(1)).printFormattedLine("Answered questions count:0"),
                () -> verify(ioService, times(1)).printFormattedLine("Right answers count:3"),
                () -> verify(ioService, times(1)).printLine("Sorry. You fail test.")
        );
    }
}