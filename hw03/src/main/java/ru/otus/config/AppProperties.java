package ru.otus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "application")
public class AppProperties implements TestConfig, TestFileNameProvider {

    private int rightAnswersCountToPass;
    private String fileName;

    @ConstructorBinding
    public AppProperties(int rightAnswersCountToPass, String fileName) {
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.fileName = fileName;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public String getTestFileName() {
        return fileName;
    }
}
