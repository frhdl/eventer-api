package org.eventer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventerApplication.class, args);
	}

}
