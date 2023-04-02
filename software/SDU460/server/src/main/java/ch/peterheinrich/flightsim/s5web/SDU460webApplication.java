package ch.peterheinrich.flightsim.s5web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.ip.dsl.Udp;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fazecast.jSerialComm.SerialPort;

@SpringBootApplication
@EnableScheduling
@EnableIntegration
public class SDU460webApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SDU460webApplication.class, args);
	}

	@Bean
	public IntegrationFlow udpIn() {
		return IntegrationFlow.from(Udp.inboundAdapter(30000))
				.channel("udpChannel")
				.get();
	}


	@Autowired 
	private SerialServer rp2040;

	@Autowired 
	private UDPServer flightSim;

	@Override
	public void run(String... args) throws Exception { 
	/* 	rp2040.openConnection();
		while(true) {
			String s = rp2040.read();
			String[] bytevals = s.split(",");
			// String was only partially transmitted!
			if(bytevals.length < 4) continue;


		}*/

	}

	


}
