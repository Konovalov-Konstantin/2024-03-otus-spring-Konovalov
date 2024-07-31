package ru.otus.spring.integration.services;

import ru.otus.spring.integration.domain.Butterfly;
import ru.otus.spring.integration.domain.Pupa;

public interface ButterflyService {

    Butterfly magicTransform(Pupa pupa);
}
