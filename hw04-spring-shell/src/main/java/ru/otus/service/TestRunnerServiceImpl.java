package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Student;

@RequiredArgsConstructor
@Service
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final ResultService resultService;

    @Override
    public void run(Student student) {
        var testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }
}
