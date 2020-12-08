/**
 * 
 */
package it.abmlb.mmw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.abmlb.mmw.model.Meteo;
import it.abmlb.mmw.repository.MeteoRepository;

/**
 * @author matteolorenzo
 *
 */
@Component
public class Scheduler {
	
	@Autowired
	MeteoRepository meteoRepository;
	
	@Scheduled(fixedRate = 30000) // questa è una prova ogni 30 secondi
	public void connectToOpenWeather() {
		Meteo m1 = new Meteo();
		m1.getFromOpenWeather("Ancona");
		meteoRepository.save(m1);
		Meteo m2 = new Meteo();
		m2.getFromOpenWeather("Milano");
		meteoRepository.save(m2);
		Meteo m3 = new Meteo();
		m3.getFromOpenWeather("Cupertino");
		meteoRepository.save(m3);
	}
}
