/**
 * 
 */
package it.abmlb.mmw.api;

import java.io.IOException;

import org.json.simple.JSONArray;
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

import it.abmlb.mmw.controller.Configurations;

/**
 *
 * Classe che espone le API per le configurazioni e gestisce le chiamate effettuabili tramite:
 * metodo HTTP GET per leggere le configurazioni attuali,
 * metodo HTTP POST per modificare le configurazioni attuali.
 * Si serve della classe Configurations per leggere o modificare le configurazioni.
 * @RestController è l'annotazione utilizzata per definire i servizi web RESTful
 * 
 * @author matteolorenzo&agnese
 *
 */
@RestController
public class ConfigurationsAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationsAPI.class);
	
	/**
	 * @Autowired viene utilizzato perchè Configurations è un componente e dunque
	 * è una classe la cui unica istanza viene gestita dal FrameWork,
	 * non istanzio io nuovi oggetti, ma utilizzo quelli tenuti in vita dal FrameWork
	 */
	@Autowired
	Configurations configurazioni;
	
	/**
	 * @RequestMapping è l'annotazione usata per definire il Request URI per accedere
	 * agli Endpoint REST
	 * @return the JSONArray containing the cities in the configurations
	 */
	@RequestMapping(value = "/config")
	public JSONArray leggiConfig() {
		return configurazioni.getConfig();
	}
	
	/**
	 * @RequestMapping è l'annotazione usata per definire il Request URI per accedere
	 * agli Endpoint REST
	 * @param confStr Stringa contenente il JSON body richiesto
	 * @RequestBody è l'annotazione usata per definire il contenuto del Request body
	 * @return the JSONObject containing information on success/failure of the operation
	 */
	@RequestMapping(value = "/config", method = RequestMethod.POST)
	public JSONObject scriviConfig(@RequestBody String confStr) {
		try {
			// se è stato parsificato tutto correttamente
			// aggiorno le configurazioni e le rendo da subito operative
			JSONArray config = (JSONArray) new JSONParser().parse(confStr);
			configurazioni.setConfig(config);
			
			return answer(0, "ok");
			
		} catch (ParseException e) {
			//altrimenti risolvo qua la situazione se ci sono stati problemi nel parsing
			logger.error(e.toString());
			return answer(1, e.toString());
		} catch (IOException e) {
			//problemi di IO
			logger.error(e.getMessage());
			return answer(2, e.toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject answer(int code, String info) {
		JSONObject answer = new JSONObject();
		answer.put("code", code);
		answer.put("info", info);
		return answer;
	}

}
