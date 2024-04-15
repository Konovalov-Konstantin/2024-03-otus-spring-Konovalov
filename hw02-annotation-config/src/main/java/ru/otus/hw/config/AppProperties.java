package ru.otus.hw.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:application.properties")
public class AppProperties implements TestConfig, TestFileNameProvider {

    // внедрить свойство из application.properties
    @Value("${application.rightAnswersCountToPass}")
    private int rightAnswersCountToPass;

    // внедрить свойство из application.properties
    @Value("${application.fileName}")
    private String testFileName;
}
