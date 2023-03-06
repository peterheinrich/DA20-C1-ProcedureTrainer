package ch.peterheinrich.flightsim.s5web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.ip.dsl.Udp;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableIntegration
public class S5webApplication {

	public static void main(String[] args) {
		SpringApplication.run(S5webApplication.class, args);
	}

	@Bean
	public IntegrationFlow udpIn() {
		return IntegrationFlow.from(Udp.inboundAdapter(30000))
				.channel("udpChannel")
				.get();
	}

	


}
