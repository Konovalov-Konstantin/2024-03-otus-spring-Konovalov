package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Answer;
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

@SpringBootTest
@DisplayName("Сервис тестирования ")
class TestServiceImplTest {
    @Autowired
    private TestServiceImpl testService;
    @MockBean
    private IOService ioService;
    @MockBean
    QuestionDao questionDao;
    List<Question> questions;

    @BeforeEach
    void init() {
        Question question = new Question("How many planets are there in the solar system?",
                List.of(new Answer("8", true), new Answer("9", false)));
        questions = List.of(question);
    }

    @DisplayName("должен возвращать количество правильных ответов студента")
    @Test
    void shouldReturnCountOfCorrectAnswers() {

        given(questionDao.getMessage("invitation")).willReturn("Please answer the questions below");
        given(questionDao.findAll()).willReturn(questions);

        given(ioService.readString()).willReturn("8");
        Student student = new Student("Vladimir", "Vladimirov");
        TestResult testResult = testService.executeTestFor(student);
        assertAll(
                () -> verify(ioService, times(1)).printLine(questions.get(0).text()),
                () -> assertEquals(1, testResult.getRightAnswersCount())
        );
    }
}