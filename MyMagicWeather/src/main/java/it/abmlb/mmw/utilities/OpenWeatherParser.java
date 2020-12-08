/**
 * 
 */
package it.abmlb.mmw.utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

/**
 * @author matteolorenzo
 *
 * Classe che realizza l'interfacciamento con OpenWeather e parsifica il JSON ricevuto
 * per popolare i suoi campi (attributi)
 *
 */
public class OpenWeatherParser {
	private String cityName;
	private Long epoch;
	private Double cloudiness;
	private Double temperature;
	private Double humidity;
	/**
	 * @param owInfo
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
	//anche questa classe potrà lanciare delle eccezioni
	public void parse() {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		RestTemplate restTemplate = new RestTemplate(); //oggetto che serve per consumare una API REST
		String result = restTemplate.getForObject(
				"http://api.openweathermap.org/data/2.5/weather?q="+this.cityName+
				"&appid=041a6d9c6975da342bee0a5f96c7a290&units=metric&lang=it", String.class);
		System.out.println(result); //giusto per vedere da console che faccia qualcosa
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
			e.printStackTrace();
		}
	}

}
