package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.config.TestConfig;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.TestResult;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void showResult(TestResult testResult) {
        String results = questionDao.getMessage("results");
        String currentStudent = questionDao.getMessage("currentStudent");
        String answered = questionDao.getMessage("answered");
        String rightAnswers = questionDao.getMessage("rightAnswers");
        String ifPassed = questionDao.getMessage("ifPassed");
        String ifFail = questionDao.getMessage("ifFail");
        ioService.printLine("");
        ioService.printLine(results);
        ioService.printFormattedLine(currentStudent + testResult.getStudent().getFullName());
        ioService.printFormattedLine(answered + testResult.getAnsweredQuestions().size());
        ioService.printFormattedLine(rightAnswers +  testResult.getRightAnswersCount());

        if (testResult.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
            ioService.printLine(ifPassed);
            return;
        }
        ioService.printLine(ifFail);
    }
}
