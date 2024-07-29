package ru.otus.spring.integration.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.Butterfly;
import ru.otus.spring.integration.domain.Pupa;

import java.util.List;

@Service
@Slf4j
public class ButterflyServiceImpl implements ButterflyService {

    @Override
    public Butterfly magicTransform(Pupa pupa) {
        List<String> colors = List.of("красного", "зеленого", "белого", "серого", "желтого", "синего", "оранжевого",
                "голубого", "фиолетового", "пурпурного");
        Butterfly butterfly = new Butterfly(colors.get(pupa.getMass() / 10));
        log.info("Появилась бабочка {} цвета", butterfly.getColor());
        return butterfly;
    }
}
