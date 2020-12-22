/**
 * 
 */
package it.abmlb.mmw.services;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.abmlb.mmw.controller.Configurations;
import it.abmlb.mmw.model.Sample;
import it.abmlb.mmw.repository.MeteoRepository;

/**
 * 
 * Classe che, attivata periodicamente dal Framework Spring, chiama l'API di OpenWeather
 * seguendo le configurazioni attuali e salva i dati nel repository
 * @Component annotazione che definisce la classe come componente autogestito da Spring
 * 
 * @author matteolorenzo&agnese
 */
@Component
public class Scheduler {
	
	/**
	 * @Autowired viene utilizzato perchè MeteoRepository è un componente e dunque
	 * è una classe la cui unica istanza viene gestita dal FrameWork
	 */
	@Autowired
	MeteoRepository meteoRepository;
	
	/**
	 * @Autowired viene utilizzato perchè Configurations è un componente e dunque
	 * è una classe la cui unica istanza viene gestita dal FrameWork
	 */
	@Autowired
	Configurations configurazioni;
	
	/**
	 * @Scheduled annotazione che indica che il metodo verrà richiamato da un TaskScheduler
	 * seguendo l'intervallo prefissato con fixedRate
	 * Il metodo chiama l'API di OpenWeather seguendo le configurazioni
	 * attuali e salva i dati nel repository
	 */
	@Scheduled(fixedRateString = "${mmw.interval}")
	public void connectToOpenWeather() {
		final Logger logger = LoggerFactory.getLogger(Scheduler.class);
		
		JSONArray lista = configurazioni.getConfig();
		
		if(StaticConfig.getCallOpenWeather()) {
			for (Object city : lista) {
				logger.info("Recupero i dati di " + city.toString());
				Sample sample = new Sample();
				sample.getFromOpenWeather(city.toString());
				meteoRepository.save(sample);
			}
		} else {
			logger.info("Chiamata ad OpenWeather non attiva");
		}
			
	}
}
