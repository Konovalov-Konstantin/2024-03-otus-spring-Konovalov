package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Student;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public Student determineCurrentStudent() {
        String firstNameMessage = questionDao.getMessage("firstName");
        String lastNameMessage = questionDao.getMessage("lastName");
        var firstName = ioService.readStringWithPrompt(firstNameMessage);
        var lastName = ioService.readStringWithPrompt(lastNameMessage);
        return new Student(firstName, lastName);
    }
}
