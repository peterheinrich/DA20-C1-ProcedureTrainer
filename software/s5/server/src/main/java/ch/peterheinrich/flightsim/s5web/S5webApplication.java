package ch.peterheinrich.flightsim.s5web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class S5webApplication {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private double count = 0;
	@Autowired
	private WebSocketController socket;


	public static void main(String[] args) {
		SpringApplication.run(S5webApplication.class, args);
	}

	@Scheduled(fixedRate = 100)
	public void reportCurrentTime() {
		count += 0.1;

		socket.send(new DataDTO("indicated-roll-deg", Math.sin(count)*10.0));
		socket.send(new DataDTO("indicated-altitude-ft", count * 10));
		socket.send(new DataDTO("indicated-pitch-deg", Math.cos(count)*10.0));

	}

}
