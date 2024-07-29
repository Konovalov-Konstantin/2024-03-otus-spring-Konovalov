package ru.otus.spring.integration.services;

import ru.otus.spring.integration.domain.Caterpillar;
import ru.otus.spring.integration.domain.Pupa;

public interface PupaService {

	Pupa transform(Caterpillar caterpillar);
}
