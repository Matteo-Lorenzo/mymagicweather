/**
 * 
 */
package it.abmlb.mmw.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

/**
 * @author matteolorenzo
 * 
 * Descrizione della classe qui
 *
 */
@Entity
@Table(name = "Meteo")
public class Meteo {
	/**
	 * @param cityName scrivere qualcosa in questi campi
	 * @param epoch
	 * @param cloudiness
	 * @param temperature
	 * @param humidity
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
	 * 
	 */
	public Meteo() {}
	
	/**
	 * @param cityName
	 * @param epoch
	 * @param cloudiness
	 * @param temperature
	 * @param humidity
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
	
	
	
}
