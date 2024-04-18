package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.config.AppProperties;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class QuestionDaoImpl implements QuestionDao {

    private final MessageSource messageSource;

    private final AppProperties properties;

    @Override
    public List<Question> findAll() {

        List<Question> questionList = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            String rawQuestion = messageSource.getMessage("question" + i, null, properties.getLocale());
            String[] splitQuestion = rawQuestion.split(";");
            String question = splitQuestion[0];
            String[] answers = splitQuestion[1].split("\\|");
            Question parsedQuestion = new Question(question, List.of(new Answer(answers[0], true),
                    new Answer(answers[1], false)));

            questionList.add(parsedQuestion);
        }
        return questionList;
    }

    @Override
    public String getMessage(String message) {
        Map<String, String> messages = new HashMap<>();
        String invitation = messageSource.getMessage("invitation", null, properties.getLocale());
        String firstName = messageSource.getMessage("firstName", null, properties.getLocale());
        String lastName = messageSource.getMessage("lastName", null, properties.getLocale());
        String results = messageSource.getMessage("results", null, properties.getLocale());
        String currentStudent = messageSource.getMessage("currentStudent", null, properties.getLocale());
        String answered = messageSource.getMessage("answered", null, properties.getLocale());
        String rightAnswers = messageSource.getMessage("rightAnswers", null, properties.getLocale());
        String ifPassed = messageSource.getMessage("ifPassed", null, properties.getLocale());
        String ifFail = messageSource.getMessage("ifFail", null, properties.getLocale());
        messages.put("invitation", invitation);
        messages.put("firstName", firstName);
        messages.put("lastName", lastName);
        messages.put("results", results);
        messages.put("currentStudent", currentStudent);
        messages.put("answered", answered);
        messages.put("rightAnswers", rightAnswers);
        messages.put("ifPassed", ifPassed);
        messages.put("ifFail", ifFail);

        return Optional.ofNullable(messages.get(message))
                .orElse(String.format("The message you are looking %s for was not found", message));
    }
}
