package it.abmlb.mmw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author matteolorenzo & agnese
 * 
 * Classe di bootstrap
 *
 */

@SpringBootApplication
@EnableScheduling
public class MyMagicWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyMagicWeatherApplication.class, args);
	}

}
