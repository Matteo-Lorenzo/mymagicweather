/**
 * 
 */
package it.abmlb.mmw.controller;

import java.security.InvalidParameterException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.abmlb.mmw.exceptions.MmwInterpolationOutOfBoundsException;
import it.abmlb.mmw.exceptions.MmwStatsException;
import it.abmlb.mmw.exceptions.MmwTimeTravelException;
import it.abmlb.mmw.model.Sample;
import it.abmlb.mmw.repository.MeteoRepository;
import it.abmlb.mmw.utilities.MmwUtils;

/**
 * 
 * Classe astratta, da cui ereditano RequestStats e RequestTrends, che definisce attributi e
 * metodi principali necessari alla gestione di richieste ricevute nelle API dell'applicativo
 * @extends MmwUtils
 * @author matteolorenzo&agnese
 */
public abstract class Request extends MmwUtils{
	
	private static final Logger logger = LoggerFactory.getLogger(Request.class);
	
	/**
	 * archivio dati su cui effettuare le ricerche
	 */
	protected MeteoRepository meteoRepository;
	/**
	 * filtro di ricerca ricevuto nelle API
	 */
	protected JSONObject filter;
	/**
	 * JSONObject usato per costruire la risposta
	 */
	protected JSONObject answer;
	/**
	 * JSONArray che conterrà le città richieste
	 */
	protected JSONArray cities;
	/**
	 * JSONObject che conterrà il periodo richiesto
	 */
	private JSONObject period;
	/**
	 * tipologia di grandezza richiesta
	 */
	protected String type;
	/**
	 * istante di partenza per la ricerca (formato UNIX)
	 */
	protected Long start;
	/**
	 * istante di fine per la ricerca (formato UNIX)
	 */
	protected Long stop;
	/**
	 * array contenente tutte le possibili grandezze
	 */
	protected String[] types = {"cloudiness", "temperature", "humidity"};
	
	/**
	 * Costruttore con parametri
	 * @param filter filtro di ricerca ricevuto nelle API
	 * @param meteoRepository archivio dati su cui effettuare le ricerche
	 */
	public Request(JSONObject filter, MeteoRepository meteoRepository) {
		this.filter = filter;
		this.meteoRepository = meteoRepository;
		this.answer = new JSONObject();
	}

	/**
	 * Metodo astratto per costruire la risposta, viene implementato nelle sottoclassi
	 * @return the result
	 * @throws MmwInterpolationOutOfBoundsException 
	 * @throws MmwStatsException 
	 */
	public abstract JSONObject getResult() throws MmwInterpolationOutOfBoundsException, MmwStatsException;
	
	/**
	 * Metodo che si occupa di effettuare un primo parsing del filter (JSONObject)
	 * ricevuto come richiesta
	 * @return true if the parsing succeded, false otherwise
	 */
	protected boolean parseRequest() {
		
		try {
		if((this.cities = (JSONArray) this.filter.get("cities")) == null)
			return false;
		if((this.period = (JSONObject) this.filter.get("period")) != null) {
			date2epoch();
		} else
			return false;
		
		if((this.type = (String) this.filter.get("type")) == null)
			return false;
		
		return true;
		
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Metodo ausiliario che si occupa di convertire stringhe contenenti date e orari
	 * espressi in formato standard in "epoche" espresse in formato UNIX
	 * @throws InvalidParameterException 
	 * @throws MmwTimeTravelException 
	 */
	private void date2epoch() throws InvalidParameterException, MmwTimeTravelException {

		String from = (String) this.period.get("from");
		String to = (String) this.period.get("to");

		if ((from != null) && (to != null)) { // il parsing è riuscito

			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
					.withZone(ZoneOffset.systemDefault());

			if ((Instant.from(fmt.parse(from)).toEpochMilli() / 1000) < (Instant.from(fmt.parse(to)).toEpochMilli()
					/ 1000)) {
				this.start = Instant.from(fmt.parse(from)).toEpochMilli() / 1000;
				this.stop = Instant.from(fmt.parse(to)).toEpochMilli() / 1000;

			} else {
				throw new MmwTimeTravelException("Intervallo non valido");
			}

		} else {
			throw new InvalidParameterException("Parametri di ricerca non validi");
		}
	}
	
	/**
	 * Metodo ausiliario protetto che si occupa di selezionare la grandezza richiesta
	 */
	protected Double getValue(Sample sample, String type) {
		switch (type) {
		case "cloudiness":
			return sample.getCloudiness();
		case "temperature":
			return sample.getTemperature();
		case "humidity":
			return sample.getHumidity();	

		default:
			return -999.0;
		}
	}

}
