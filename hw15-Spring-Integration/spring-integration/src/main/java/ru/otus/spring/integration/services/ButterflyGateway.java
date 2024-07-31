package ru.otus.spring.integration.services;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.integration.domain.Butterfly;
import ru.otus.spring.integration.domain.Caterpillar;
import ru.otus.spring.integration.domain.Pupa;

@MessagingGateway
public interface ButterflyGateway {

	@Gateway(requestChannel = "caterpillarChannel", replyChannel = "pupaChannel")
	Pupa process(Caterpillar caterpillar);

	@Gateway(requestChannel = "pupaChannel", replyChannel = "butterflyChannel")
	Butterfly process(Pupa pupa);
}
