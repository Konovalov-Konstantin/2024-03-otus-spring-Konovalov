package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    /**
     Аутентификация:
     admin / admin
     user / user
     */

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
