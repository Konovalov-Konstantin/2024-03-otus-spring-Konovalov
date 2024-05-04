package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Student;
import ru.otus.security.LoginContext;
import ru.otus.service.TestRunnerService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShell {

    private final TestRunnerService testRunnerService;

    private final LoginContext loginContext;

    private final QuestionDao questionDao;

    private Student student;

    @ShellMethod(value = "Introduce command", key = {"i", "introduce"})
    public String introduce(@ShellOption String firstName, @ShellOption String lastName) {
        loginContext.login(firstName, lastName);
        if (loginContext.isLoggedIn()) {
            this.student = new Student(firstName, lastName);
            String welcome = questionDao.getMessage("welcome");
            return String.format(welcome + ": %s %s", lastName, firstName);
        }
        return questionDao.getMessage("insertNameAndFirstname");
    }

    @ShellMethod(value = "Test command", key = {"t", "test"})
    @ShellMethodAvailability(value = "isTestAvailable")
    public void startTest() {
        testRunnerService.run(this.student);
    }

    private Availability isTestAvailable() {
        return loginContext.isLoggedIn()
                ? Availability.available()
                : Availability.unavailable(questionDao.getMessage("insertNameAndFirstname"));
    }
}
