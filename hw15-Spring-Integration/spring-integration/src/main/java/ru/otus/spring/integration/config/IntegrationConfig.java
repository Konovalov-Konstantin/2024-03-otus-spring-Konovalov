package ru.otus.spring.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

import ru.otus.spring.integration.domain.Pupa;
import ru.otus.spring.integration.services.ButterflyService;
import ru.otus.spring.integration.services.PupaService;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> caterpillarChannel() {
        return MessageChannels.queue(10);
    }

    @Bean
    public MessageChannelSpec<?, ?> pupaChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public MessageChannelSpec<?, ?> butterflyChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(5000).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow caterpillarFlow(PupaService pupaService) {
        return IntegrationFlow.from(caterpillarChannel())
                .handle(pupaService, "transform")
                .<Pupa>filter(p -> p.getMass() > 50)
                .channel(pupaChannel())
                .get();
    }

    @Bean
    public IntegrationFlow butterflyFlow(ButterflyService butterflyService) {
        return IntegrationFlow.from(pupaChannel())
                .handle(butterflyService, "magicTransform")
                .channel(butterflyChannel())
                .get();
    }
}
