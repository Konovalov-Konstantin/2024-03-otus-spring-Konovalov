package ru.otus.commandlinerunners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.service.TestRunnerService;

@Component
public class StudentTestRunner implements CommandLineRunner {

    private final TestRunnerService testRunnerService;

    public StudentTestRunner(TestRunnerService testRunnerService) {
        this.testRunnerService = testRunnerService;
    }

    @Override
    public void run(String... args) {
        testRunnerService.run();
    }
}
