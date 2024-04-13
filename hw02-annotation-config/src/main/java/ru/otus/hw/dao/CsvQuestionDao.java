package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CsvQuestionDao implements QuestionDao {

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        String testFileName = fileNameProvider.getTestFileName();
        List<Question> questionList = new ArrayList<>();

        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(testFileName);
             Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            List<QuestionDto> questionDtos = new CsvToBeanBuilder<QuestionDto>(bufferedReader)
                    .withType(QuestionDto.class)
                    .withSkipLines(1)
                    .withSeparator(';')
                    .build().parse();

            questionDtos.stream().map(QuestionDto::toDomainObject).forEach(questionList::add);
        } catch (IOException e) {
            // Использовать QuestionReadException
            // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/
            throw new QuestionReadException(e.getMessage(), e);
        }
        return questionList;
    }
}
