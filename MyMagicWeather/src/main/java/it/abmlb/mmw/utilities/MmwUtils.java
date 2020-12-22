/**
 * 
 */
package it.abmlb.mmw.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * 
 * Classe di Utility con metodi di utilizzo generale, viene estesa da diverse classi
 * che neccessitano di ereditarne i metodi
 * 
 * @author matteolorenzo&agnese
 */
public class MmwUtils {

	/**
	 * Metodo per effettuare arrotondamenti a due cifre decimali su tipi double
	 * @param val Valore da arrotondare
	 * @return the rounded value
	 */
	protected double round(double val) {
		int rounder = (int) (val*100.0);
		return (double)rounder/100;
	}
	
	/**
	 * Metodo per trasformare timeStamp espressi in formato UNIX in stringhe
	 * con formattazione di data e ora
	 * @param milliseconds Valore in millisecondi da formattare
	 * @return formatted date String
	 */
	protected String toDateStr(Long milliseconds) {
	    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(milliseconds);
	    return dateFormat.format(cal.getTime());
	}
	
	/**
	 * Metodo utilizzato per costruire un JSONObject standard di risposta
	 * @param code Codice da settare
	 * @param info Stringa di informazioni
	 * @param time Tempo impiegato
	 * @return the answer
	 */
	@SuppressWarnings("unchecked")
	protected JSONObject buildAnswer(int code, String info, Long time) {
		JSONObject answer = new JSONObject();
		answer.put("code", code);
		answer.put("info", info);
		answer.put("time", time);
		answer.put("result", new JSONArray());
		return answer;
	}
}
