package ru.otus.security;

import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class LoginContextImpl implements LoginContext {

    private String firstName;

    private String lastName;

    @Override
    public void login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean isLoggedIn() {
        return nonNull(firstName)  && nonNull(lastName);
    }

}
