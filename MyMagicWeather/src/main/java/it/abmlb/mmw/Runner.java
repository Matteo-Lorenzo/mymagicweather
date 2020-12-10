/**
 * 
 */
package it.abmlb.mmw;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.abmlb.mmw.model.Meteo;
import it.abmlb.mmw.repository.MeteoRepository;

/**
 * @author matteolorenzo & brugl
 * 
 * Classe di test che riempie l'archivio dati, non sarà utilizzata in produzione
 *
 */
@Component
public class Runner implements CommandLineRunner {
	
	@Autowired
	MeteoRepository meteoRepository;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Questa è una prova di messaggio di lancio!");
		
		//meteoRepository.save(m1);
		Random generator = new Random();
		if(false) {
			for (int i = 0; i < 10; i++) {
				Meteo meteo = new Meteo("Milano", 100000L+(i*3600L), round(generator.nextDouble()*100),
						round(generator.nextDouble()*25), round(generator.nextDouble()*100));
				meteoRepository.save(meteo);
			}
			for (int i = 0; i < 10; i++) {
				Meteo meteo = new Meteo("Genova", 100900L+(i*4000L), round(generator.nextDouble()*100),
						round(generator.nextDouble()*25), round(generator.nextDouble()*100));
				meteoRepository.save(meteo);
			}
		}
		
	}

	private double round(double val) {
		int rounder = (int) (val*100.0);
		return (double)rounder/100;
	}
}
