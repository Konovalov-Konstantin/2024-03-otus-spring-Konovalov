package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.hw.service.TestRunnerService;

@ComponentScan
public class App {
    public static void main(String[] args) {

        //Создать контекст на основе Annotation/Java конфигурирования
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();
    }
}