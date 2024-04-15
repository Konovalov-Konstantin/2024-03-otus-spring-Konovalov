package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис тестрирования ")
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {
    private TestServiceImpl testService;
    private CsvQuestionDao csvQuestionDao;
    @Mock
    private TestFileNameProvider fileNameProvider;
    @Mock
    private IOService ioService;

    List<Question> questions;

    @BeforeEach
    void setUp() {
        given(fileNameProvider.getTestFileName()).willReturn("questions.csv");
        csvQuestionDao = new CsvQuestionDao(fileNameProvider);
        questions = csvQuestionDao.findAll();
        testService = new TestServiceImpl(ioService, csvQuestionDao);
    }

    @DisplayName("должен возвращать количество правильных ответов студента")
    @Test
    void shouldCorrectReadNameAndSurname() {
        given(ioService.readString()).willReturn("yes");
        Student student = new Student("Vladimir", "Vladimirov");
        TestResult testResult = testService.executeTestFor(student);
        assertAll(
                () -> verify(ioService, times(1)).printLine(questions.get(0).text()),
                () -> assertEquals(1, testResult.getRightAnswersCount())
        );
    }
}