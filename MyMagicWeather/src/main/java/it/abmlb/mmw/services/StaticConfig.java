/**
 * 
 */
package it.abmlb.mmw.services;

/**
 * @author matteolorenzo & agnese
 * Classe wrapper che con i suoi metodi e attributi statici mette a disposizione
 * anche agli oggetti istanziati dall'applicativo e non autogestiti dal framework
 * le configurazioni presenti in application.properties
 */
public class StaticConfig {
	
	/**
	 * @param apikey apikey per il collegamento a OpenWeather
	 * @param offset offset per gestire le code dell'interpolatore
	 * @param callOpenWeather flag booleano che attiva/disattiva la lettura schedulata da OpenWeather
	 */
	private static String apikey;
	private static Long offset;
	private static Boolean callOpenWeather;
	
	/**
	 * @return the apikey
	 */
	public static String getApikey() {
		return apikey;
	}
	/**
	 * @param apikey the apikey to set
	 */
	public static void setApikey(String apikey) {
		StaticConfig.apikey = apikey;
	}
	/**
	 * @return the offset
	 */
	public static Long getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public static void setOffset(Long offset) {
		StaticConfig.offset = offset;
	}
	/**
	 * @return the callOpenWeather
	 */
	public static Boolean getCallOpenWeather() {
		return callOpenWeather;
	}
	/**
	 * @param callOpenWeather the callOpenWeather to set
	 */
	public static void setCallOpenWeather(Boolean callOpenWeather) {
		StaticConfig.callOpenWeather = callOpenWeather;
	}
	
}
