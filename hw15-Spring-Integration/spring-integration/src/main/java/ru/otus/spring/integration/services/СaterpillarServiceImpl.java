package ru.otus.spring.integration.services;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.otus.spring.integration.domain.Caterpillar;

@Service
@Slf4j
public class СaterpillarServiceImpl implements СaterpillarService {

    private final ButterflyGateway butterflyGateway;

    public СaterpillarServiceImpl(ButterflyGateway butterflyGateway) {
        this.butterflyGateway = butterflyGateway;
    }

    @Override
    public void start() {
        for (int i = 0; i < 10; i++) {
            Caterpillar caterpillar = new Caterpillar(RandomUtils.nextInt(4, 9));
            log.info("Родилась гусеница {} см", caterpillar.getLength());
            butterflyGateway.process(caterpillar);
            pause();
        }
    }

    private void pause() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
