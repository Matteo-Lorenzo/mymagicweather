/**
 * 
 */
package it.abmlb.mmw.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import it.abmlb.mmw.utilities.OpenWeatherParser;

import javax.persistence.Id;

/**
 * @author matteolorenzo & brugl
 * 
 * Classe che descrive il modello dei dati contenuti nell'archivio
 *
 */
@Entity
@Table(name = "Meteo")
public class Meteo {
	/**
	 * @param cityName Nome della città
	 * @param epoch Istante temporale delle misurazioni, espresso in formato UNIX
	 * @param cloudiness Percentuale di nuvolosità
	 * @param temperature Temperatura media
	 * @param humidity Percentuale di umidità relativa
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	private String cityName;
	private Long epoch;
	private Double cloudiness;
	private Double temperature;
	private Double humidity;
	
	/**
	 * Costruttore di default
	 */
	public Meteo() {}
	
	/**
	 * Costruttore con parametri
	 * @param cityName Nome della città
	 * @param epoch Istante temporale delle misurazioni, espresso in formato UNIX
	 * @param cloudiness Percentuale di nuvolosità
	 * @param temperature Temperatura media
	 * @param humidity Percentuale di umidità relativa
	 */
	public Meteo(String cityName, Long epoch, Double cloudiness, Double temperature, Double humidity) {
		this.cityName = cityName;
		this.epoch = epoch;
		this.cloudiness = cloudiness;
		this.temperature = temperature;
		this.humidity = humidity;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the epoch
	 */
	public Long getEpoch() {
		return epoch;
	}

	/**
	 * @param epoch the epoch to set
	 */
	public void setEpoch(Long epoch) {
		this.epoch = epoch;
	}

	/**
	 * @return the cloudiness
	 */
	public Double getCloudiness() {
		return cloudiness;
	}

	/**
	 * @param cloudiness the cloudiness to set
	 */
	public void setCloudiness(Double cloudiness) {
		this.cloudiness = cloudiness;
	}

	/**
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the humidity
	 */
	public Double getHumidity() {
		return humidity;
	}

	/**
	 * @param humidity the humidity to set
	 */
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	
	//da sollevare qui varie eccezioni se qualcosa non va
	public void getFromOpenWeather(String cityName) {
		OpenWeatherParser openWeatherParser = new OpenWeatherParser(cityName);
		openWeatherParser.parse();
		this.cityName = openWeatherParser.getCityName();
		this.epoch = openWeatherParser.getEpoch();
		this.cloudiness = openWeatherParser.getCloudiness();
		this.temperature = openWeatherParser.getTemperature();
		this.humidity = openWeatherParser.getHumidity();
	}
	
}
