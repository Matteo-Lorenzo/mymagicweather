/**
 * 
 */
package it.abmlb.mmw.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import it.abmlb.mmw.services.StaticConfig;

/**
 *
 * Classe che realizza l'interfacciamento con OpenWeather e parsifica il JSON ricevuto
 * per popolare i suoi campi (attributi)
 *
 * @author matteolorenzo&agnese
 */
public class OpenWeatherParser {
	
	/**
	 * Nome della città
	 */
	private String cityName;
	/**
	 * Istante temporale delle misurazioni, espresso in formato UNIX
	 */
	private Long epoch;
	/**
	 * Percentuale di nuvolosità
	 */
	private Double cloudiness;
	/**
	 * Temperatura media
	 */
	private Double temperature;
	/**
	 * Percentuale di umidità relativa
	 */
	private Double humidity;
	
	private static final Logger logger = LoggerFactory.getLogger(OpenWeatherParser.class);
	
	/**
	 * Costruttore con parametro
	 * @param cityName Nome della città su cui effettuare la richiesta a OpenWeather
	 */
	public OpenWeatherParser(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @return the epoch
	 */
	public Long getEpoch() {
		return epoch;
	}
	/**
	 * @return the cloudiness
	 */
	public Double getCloudiness() {
		return cloudiness;
	}
	/**
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}
	/**
	 * @return the humidity
	 */
	public Double getHumidity() {
		return humidity;
	}
	
	/**
	 * Metodo per realizzare l'interfacciamento con OpenWeather
	 * Parsifica il JSON ricevuto per popolare i suoi campi (attributi)
	 */
	public void parse() {
		
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		RestTemplate restTemplate = new RestTemplate(); //oggetto che serve per consumare una API REST
		String result = restTemplate.getForObject(
				"http://api.openweathermap.org/data/2.5/weather?q="+this.cityName+
				"&appid="+StaticConfig.getApikey()+"&units=metric&lang=it", String.class);
		logger.info(result); //giusto per vedere da console l'operazione effettuata
		
		try {
			obj = (JSONObject) parser.parse(result);
			this.cityName = (String) obj.get("name");
			this.epoch = (Long) obj.get("dt");
			JSONObject clouds = (JSONObject) obj.get("clouds");
			this.cloudiness = Double.parseDouble(clouds.get("all").toString());
			JSONObject main = (JSONObject) obj.get("main");
			this.temperature = Double.parseDouble(main.get("temp").toString());
			this.humidity = Double.parseDouble(main.get("humidity").toString());
		
		} catch (ParseException e) {
			logger.error(e.toString());
		}
	}

}
