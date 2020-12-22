/**
 * 
 */
package it.abmlb.mmw.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.abmlb.mmw.controller.Configurations;

/**
 * @author matteolorenzo&agnese
 *
 */
public class ConfigurationsTest {
	
	private Configurations configurations;
	private JSONArray conf;
	
	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setUp() {
		configurations = new Configurations();
		conf = new JSONArray();
		conf.add("Montappone");
		conf.add("Milano");
		conf.add("Ancona");
		conf.add("Parigi");
		conf.add("Napoli");
		try {
			configurations.setConfig(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterEach
	public void tearDown() {}
	
	@Test
	@DisplayName("Test risposta di configurazioni dinamiche")
	public void test() {
		assertEquals(conf, configurations.getConfig());
	}

}
