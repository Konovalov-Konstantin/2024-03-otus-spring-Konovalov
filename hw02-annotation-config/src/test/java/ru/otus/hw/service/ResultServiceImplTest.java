package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис вывода результатов ")
@ExtendWith(MockitoExtension.class)
class ResultServiceImplTest {

    private ResultServiceImpl resultService;

    @Mock
    IOService ioService;

    @Mock
    TestConfig testConfig;

    @BeforeEach
    void setUp() {
        resultService = new ResultServiceImpl(testConfig, ioService);
    }

    @DisplayName("должен корректно выводить результаты тестирования студента ")
    @Test
    void shouldCorrectReturnTestResult() {

        given(testConfig.getRightAnswersCountToPass()).willReturn(4);

        Student student = new Student("name", "surname");
        TestResult testResult = new TestResult(student);

        testResult.setRightAnswersCount(3);

        resultService.showResult(testResult);

        assertAll(
                () -> verify(ioService, times(3)).printLine(anyString()),
                () -> verify(ioService, times(1)).printFormattedLine("Student: %s", student.getFullName()),
                () -> verify(ioService, times(1)).printFormattedLine("Answered questions count: %d", 0),
                () -> verify(ioService, times(1)).printFormattedLine("Right answers count: %d", 3)
        );
    }
}