package ru.otus.hw.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class HealthCheck implements HealthIndicator {

    //  http://localhost:8080/actuator/health

    @Override
    public Health health() {

        int randomHealth = new Random().nextInt(0, 100);

        Health.Builder status = Health.up();

        if (randomHealth > 90) {
            status = Health.down();
        }
        return status.build();
    }
}
