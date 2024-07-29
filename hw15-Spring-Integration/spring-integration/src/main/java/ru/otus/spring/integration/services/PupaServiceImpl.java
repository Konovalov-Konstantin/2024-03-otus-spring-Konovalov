package ru.otus.spring.integration.services;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.otus.spring.integration.domain.Caterpillar;
import ru.otus.spring.integration.domain.Pupa;

@Service
@Slf4j
public class PupaServiceImpl implements PupaService {

    @Override
    public Pupa transform(Caterpillar caterpillar) {
        Pupa pupa = new Pupa(caterpillar.getLength() * RandomUtils.nextInt(5, 10));
        log.info("Родилась пупа массой {}", pupa.getMass());
        return pupa;
    }
}
