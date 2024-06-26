package ru.otus.dao;

import ru.otus.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();

    String getMessage(String message);
}
