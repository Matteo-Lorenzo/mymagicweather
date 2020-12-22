/**
 * 
 */
package it.abmlb.mmw.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.abmlb.mmw.controller.RequestStats;
import it.abmlb.mmw.exceptions.MmwStatsException;
import it.abmlb.mmw.repository.MeteoRepository;
import it.abmlb.mmw.utilities.MmwUtils;

/**
 *
 * Classe che espone le API per le statistiche e gestisce le chiamate effettuabili tramite
 * metodo HTTP POST per richiedere le statistiche per una lista di città
 * in un dato periodo per una o più grandezze.
 * Si serve della classe RequestStats per costruire il JSONObject di risposta
 * che conterrà le informazioni richieste.
 * @RestController è l'annotazione utilizzata per definire i servizi web RESTful
 * 
 * @author matteolorenzo&agnese
 */
@SuppressWarnings("unchecked")
@RestController
public class StatsAPI extends MmwUtils{
	
	private static final Logger logger = LoggerFactory.getLogger(StatsAPI.class);
	
	/**
	 * @Autowired viene utilizzato perchè MeteoRepository è un componente,
	 * più specificamente un @Repository, e dunque
	 * è una classe la cui unica istanza viene gestita dal FrameWork,
	 * non istanzio io nuovi oggetti, ma utilizzo quelli tenuti in vita dal FrameWork
	 */
	@Autowired
	MeteoRepository meteoRepository;

	/**
	 * @RequestMapping è l'annotazione usata per definire il Request URI per accedere
	 * agli Endpoint REST
	 * @param filterStr Stringa contenente il JSON body richiesto
	 * @RequestBody è l'annotazione usata per definire il contenuto del Request body
	 * @return the JSONObject containing the answer with the required statistics
	 */
	@RequestMapping(value = "/stats", method = RequestMethod.POST)
	public JSONObject stats(@RequestBody String filterStr) {
		JSONObject answer = new JSONObject();
		
		Long time = 0L;
		try {
			
			time = System.currentTimeMillis();
			JSONObject filter = (JSONObject) new JSONParser().parse(filterStr);
			RequestStats stats = new RequestStats(filter, meteoRepository);
			answer = stats.getResult();
			time = System.currentTimeMillis() - time;
			answer.put("time", time);
			if ("".equals(answer.get("info"))) {
				answer.put("info", "Elapsed time " + time.toString() + "ms");				
			}
		} catch (ParseException e) {
			
			//altrimenti risolvo qua la situazione se ci sono stati problemi nel parsing
			time = System.currentTimeMillis() - time;
			answer = buildAnswer(1, e.toString(), time);
			logger.error(e.toString());
		} catch (ClassCastException e) {
			
			//problemi di Casting
			time = System.currentTimeMillis() - time;
			answer = buildAnswer(2, e.getMessage(), time);
			logger.error(e.toString());
		} catch (MmwStatsException e) {
			
			//problemi a trovare la campioni
			time = System.currentTimeMillis() - time;
			answer = buildAnswer(5, e.getMessage(), time);
			logger.error(e.toString());
		} catch (Exception e) {
			
			//problemi non riconosciuti
			time = System.currentTimeMillis() - time;
			answer = buildAnswer(99, e.getMessage(), time);
			logger.error(e.toString());
		}
		return answer;
	}

}
