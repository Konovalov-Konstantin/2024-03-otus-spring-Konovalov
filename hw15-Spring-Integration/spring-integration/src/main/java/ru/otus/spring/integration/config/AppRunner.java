package ru.otus.spring.integration.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.spring.integration.services.СaterpillarService;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

	private final СaterpillarService service;

	@Override
	public void run(String... args) {
		service.start();
	}
}
