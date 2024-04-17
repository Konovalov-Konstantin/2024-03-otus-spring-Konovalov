package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Student;
import ru.otus.domain.TestResult;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;


    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            var isAnswerValid = false; // Задать вопрос, получить ответ
            ioService.printLine(question.text());
            String studentAnswer = ioService.readString();
            List<Answer> answers = question.answers();

            isAnswerValid = answers.stream()
                    .filter(x -> x.text().equalsIgnoreCase(studentAnswer))
                    .map(Answer::isCorrect)
                    .findFirst().orElse(false);

            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
