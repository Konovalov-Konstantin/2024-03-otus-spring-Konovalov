package ru.otus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class AppProperties implements TestConfig, TestFileNameProvider {

    private int rightAnswersCountToPass;

    private String testFileName;

    private Locale locale;

    @ConstructorBinding
    public AppProperties(int rightAnswersCountToPass, String testFileName, Locale locale) {
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.testFileName = testFileName;
        this.locale = locale;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public String getTestFileName() {
        return testFileName;
    }

    public Locale getLocale() {
        return locale;
    }
}
