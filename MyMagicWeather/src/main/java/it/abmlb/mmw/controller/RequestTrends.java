/**
 * 
 */
package it.abmlb.mmw.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import it.abmlb.mmw.exceptions.MmwInterpolationOutOfBoundsException;
import it.abmlb.mmw.model.Point;
import it.abmlb.mmw.model.Sample;
import it.abmlb.mmw.repository.MeteoRepository;
import it.abmlb.mmw.services.StaticConfig;
import it.abmlb.mmw.utilities.Interpolator;

/**
 * 
 * Classe che eredita da Request e definisce ulteriori metodi specializzati
 * necessari alla gestione di richieste relative al calcolo di Andamenti
 * @extends Request
 *
 * @author matteolorenzo&agnese
 */
@SuppressWarnings("unchecked")
public class RequestTrends extends Request {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestTrends.class);
	
	/**
	 * intervallo di campionamento
	 */
	private Long interval;

	/**
	 * Costruttore con parametri
	 * @param filter filtro di ricerca ricevuto nelle API
	 * @param meteoRepository archivio dati su cui effettuare le ricerche
	 */
	public RequestTrends(JSONObject filter, MeteoRepository meteoRepository) {
		super(filter, meteoRepository);
	}

	/**
	 * Metodo che si occupa di effettuare un parsing ulteriore del filter (JSONObject)
	 * ricevuto come richiesta
	 * richiama il metodo parseRequest della superclasse
	 * @return true if the parsing succeded, false otherwise
	 */
	@Override
	protected boolean parseRequest() {
		try {
		if((this.interval = (Long) this.filter.get("interval")) != null) {
			this.interval *= 60; // passo da minuti a secondi
		} else
			return false;
		} catch (Exception e) {
			return false;
		}
		return super.parseRequest();
	}

	/**
	 * Overriding del metodo astratto della superclasse
	 * Si occupa di ritornare la risposta in formato JSON al chiamante
	 * @throws MmwInterpolationOutOfBoundsException 
	 */
	@Override
	public JSONObject getResult() throws MmwInterpolationOutOfBoundsException {
		if (parseRequest()) {
			
			JSONArray result = new JSONArray();
			this.answer.put("code", 0);
			this.answer.put("info", "");
			
			for (Object city : cities) {
				String cityName = (String) city;
				logger.info(cityName);
				if (this.type.equals("all")) {
					
					for (String type : this.types) {
						result.add(calcTrend(cityName, type));
					}
				
				} else {
					result.add(calcTrend(cityName, this.type));
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
	 * Istanzia un oggetto di Interpolator performando i calcoli richiesti
	 * sui dati estratti
	 * @throws MmwInterpolationOutOfBoundsException 
	 */
	private JSONObject calcTrend(String cityName, String type) throws MmwInterpolationOutOfBoundsException {
		//prendo campioni da 90 minuti prima a 90 minuti dopo dell'intervallo richiesto per essere sicuro di
		//gestire bene i campionamenti in testa e in coda all'intervallo, se poi uno esagera nella
		//richiesta il Fuori Range viene gestito dall'interpolatore stesso che genera un'opportuna eccezione
		Long offset = StaticConfig.getOffset(); //di default 90 minuti espressi in secondi
		
		List<Sample> samples = meteoRepository.findSamples(cityName, this.start-offset, this.stop+offset);
		JSONObject resultForCity = new JSONObject();
		resultForCity.put("cityname", cityName);
		resultForCity.put("type", type);
		
		JSONArray data = new JSONArray();
		Interpolator interpolator = new Interpolator();
		
		for (Sample sample : samples) {
			interpolator.addSample(new Point(sample.getEpoch(), getValue(sample, type)));
		}
		
		for (Long x = this.start; x <= this.stop; x+=this.interval) {
			Point interpolato = interpolator.valueFor(new Point(x, 0.0));
			JSONObject obj = new JSONObject();
			
			obj.put("datetime", toDateStr(interpolato.getX()*1000));
			obj.put("value", interpolato.getY());
			obj.put("score", interpolato.getScore());
			data.add(obj);
		}
		
		resultForCity.put("data", data);
		return resultForCity;
	}
	
}
