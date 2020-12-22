/**
 * 
 */
package it.abmlb.mmw.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

/**
 * 
 * Classe che, richiamata da ConfigurationsAPI, permette di leggere o modificare le configurazioni
 * dinamiche dell'applicativo
 * @Component annotazione che definisce la classe come componente autogestito da Spring
 * 
 * @author matteolorenzo&agnese
 */
@Component
public class Configurations {
	
	private static final Logger logger = LoggerFactory.getLogger(Configurations.class);
	
	/**
	 * costante che indica la path dove si trova il file JSON di 
	 * configurazioni dinamiche
	 */
	 private final String filePath = "config.json";
	
	 /** 
	 * JSONArray contenente la copia in memoria delle configurazioni
	 */
	private JSONArray config;

	/**
	 * Costruttore
	 */
	public Configurations() {
		super();
		this.config = new JSONArray();
	}

	/**
	 * Metodo che si occupa di ritornare le configurazioni al chiamante,
	 * attuando diversi controlli sulla presenza di configurazioni in memoria e su file
	 * Se non trova configurazioni in memoria o su file, crea delle configurazioni di default
	 * @return the config
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getConfig() {
		
		if (this.config.size() == 0) {
			File f = new File(this.filePath);
			if(f.isFile()) { // se è un file, provo a leggerlo
				
				try {
					BufferedReader br = new BufferedReader(new FileReader(this.filePath));
					this.config = (JSONArray) new JSONParser().parse(br);
					br.close();
				} catch (FileNotFoundException e) {
					
					logger.error(e.getMessage());
				} catch (IOException e) {
					
					logger.error(e.getMessage());
				} catch (ParseException e) {
					
					logger.error(e.toString());
				}
			} else {
				JSONArray stdConfig = new JSONArray();
				stdConfig.add("Montappone");
				try {
					this.setConfig(stdConfig);
				} catch (IOException e) {
					//problemi di IO
					logger.error(e.getMessage());
				}
			}
		}
		return this.config;
	}

	/**
	 * Metodo che si occupa di settare le nuove configurazioni dinamiche nel relativo attributo
	 * e di riportare gli stessi cambiamenti anche su file di testo in formato JSON in modo
	 * da poterli recuperare ad un nuovo avvio dell'applicativo
	 * @param config the config to set
	 * @throws IOException 
	 */
	public void setConfig(JSONArray config) throws IOException {
		this.config = config;
		
		try {
			FileWriter file = new FileWriter(this.filePath); // il file viene aperto in modalità sovrascrittura
			BufferedWriter bw = new BufferedWriter(file);
            bw.write(config.toJSONString());
            bw.close();
        } catch (IOException e) {
        	
        	//scrivo sul log se ci sono stati problemi con file su disco
        	logger.error(e.getMessage());
        	throw e;
        }
	}
	
	
	
}
