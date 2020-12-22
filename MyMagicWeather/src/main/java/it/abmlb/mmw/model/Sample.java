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
 * 
 * Classe che descrive il modello dei dati contenuti nell'archivio
 * @Entity è un'annotazione che specifica che la classe è un'entità ed è mappata in una database Table.
 * @Table è un'annotazione che specifica il nome della tabella nel database usata per la mappatura.
 *
 * @author matteolorenzo&agnese
 */
@Entity
@Table(name = "Meteo")
public class Sample {
	
	/**
	 * identificatore autogenerato dell'entità
	 * @Id è un'annotazione che specifica l'identificatore di un'entità e
	 * @GeneratedValue si occupa di specificare la strategia di generazione per i valori di questi identificatori
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
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
	
	/**
	 * Costruttore di default
	 */
	public Sample() {}
	
	/**
	 * Costruttore con parametri
	 * @param cityName Nome della città
	 * @param epoch Istante temporale delle misurazioni, espresso in formato UNIX
	 * @param cloudiness Percentuale di nuvolosità
	 * @param temperature Temperatura media
	 * @param humidity Percentuale di umidità relativa
	 */
	public Sample(String cityName, Long epoch, Double cloudiness, Double temperature, Double humidity) {
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
	
	/**
	 * metodo che si occupa di richiamare il parser per ricevere e assegnare le informazioni
	 * ottenute come risposta da OpenWeather negli attributi dell'entità Sample
	 */
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
