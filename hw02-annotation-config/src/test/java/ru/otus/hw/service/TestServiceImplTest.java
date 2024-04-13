package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис тестрирования ")
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    private TestServiceImpl testService;

    @Mock
    IOService ioService;

    @Mock
    QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        testService = new TestServiceImpl(ioService, questionDao);
    }


    @DisplayName("должен возвращать количество правильных ответов студента")
    @Test
    void shouldCorrectReadNameAndSurname() {

        Answer correctAnswer = new Answer("testCorrectAnswer", true);
        Answer inCorrectAnswer = new Answer("testInCorrectAnswer", false);

        Question question = new Question("testQuestion", List.of(correctAnswer, inCorrectAnswer));

        List<Question> questions = List.of(question);

        given(questionDao.findAll()).willReturn(questions);
        given(ioService.readString()).willReturn("testCorrectAnswer");

        Student student = new Student("Vladimir", "Vladimirov");

        TestResult testResult = testService.executeTestFor(student);

        assertEquals(1, testResult.getRightAnswersCount());

    }
}