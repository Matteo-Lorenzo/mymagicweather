/**
 * 
 */
package it.abmlb.mmw.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.abmlb.mmw.exceptions.MmwStatsException;
import it.abmlb.mmw.model.Sample;
import it.abmlb.mmw.repository.MeteoRepository;
import it.abmlb.mmw.utilities.StatCalculator;

/**
 * 
 * Classe che eredita da Request e definisce ulteriori metodi specializzati
 * necessari alla gestione di richieste relative al calcolo di Statistiche
 * @extends Request
 * 
 * @author matteolorenzo&agnese
 */
@SuppressWarnings("unchecked")
public class RequestStats extends Request {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestStats.class);

	/**
	 * Costruttore con parametri
	 * @param filter filtro di ricerca ricevuto nelle API
	 * @param meteoRepository archivio dati su cui effettuare le ricerche
	 */
	public RequestStats(JSONObject filter, MeteoRepository meteoRepository) {
		super(filter, meteoRepository);
	}

	/**
	 * Overriding del metodo astratto della superclasse
	 * Si occupa di ritornare la risposta in formato JSON al chiamante
	 * @throws MmwStatsException 
	 */
	@Override
	public JSONObject getResult() throws MmwStatsException {
		if (parseRequest()) {
			
			JSONArray result = new JSONArray();
			this.answer.put("code", 0);
			this.answer.put("info", "");
			
			for (Object city : cities) {
				String cityName = (String) city;
				logger.info(cityName); //per controllare
				if (this.type.equals("all")) {
					for (String type : this.types) {
						result.add(calcStat(cityName, type));
					}
				
				} else {
					result.add(calcStat(cityName, this.type));
				}
			}
			this.answer.put("result", result);
			
		} else {
			this.answer = buildAnswer(3, "Filter not OK", 0L);
		}
		return this.answer;
	}
	
	/**
	 * Metodo che si occupa di costruire la risposta in formato JSON 
	 * da ritornare al chiamante
	 * Effettua la query al repository
	 * Istanzia un oggetto di StatCalculator performando i calcoli richiesti
	 * sui dati estratti
	 * @throws MmwStatsException 
	 */
	private JSONObject calcStat(String cityName, String type) throws MmwStatsException {
		
		List<Sample> samples = meteoRepository.findSamples(cityName, this.start, this.stop);
		
		JSONObject resultForCity = new JSONObject();
		resultForCity.put("cityname", cityName);
		resultForCity.put("type", type);
		
		StatCalculator statCalculator = new StatCalculator();
		for (Sample sample : samples) {
			statCalculator.addSample(getValue(sample, type));
		}
		
		JSONObject data = new JSONObject();
		data.put("max", statCalculator.getMax());
		data.put("min", statCalculator.getMin());
		data.put("average", statCalculator.getAverage());
		data.put("variance", statCalculator.getVariance());
		resultForCity.put("data", data);
		return resultForCity;
	}

}
