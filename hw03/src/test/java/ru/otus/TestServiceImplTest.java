package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.config.TestFileNameProvider;
import ru.otus.dao.CsvQuestionDao;
import ru.otus.domain.Question;
import ru.otus.domain.Student;
import ru.otus.domain.TestResult;
import ru.otus.service.IOService;
import ru.otus.service.TestServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис тестирования ")
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
    void shouldReturnCountOfCorrectAnswers() {
        given(ioService.readString()).willReturn("8");
        Student student = new Student("Vladimir", "Vladimirov");
        TestResult testResult = testService.executeTestFor(student);
        assertAll(
                () -> verify(ioService, times(1)).printLine(questions.get(0).text()),
                () -> assertEquals(1, testResult.getRightAnswersCount())
        );
    }
}