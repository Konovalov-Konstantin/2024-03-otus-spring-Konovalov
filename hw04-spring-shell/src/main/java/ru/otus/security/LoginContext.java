package ru.otus.security;

public interface LoginContext {

    void login(String firstName, String lastName);

    boolean isLoggedIn();
}
