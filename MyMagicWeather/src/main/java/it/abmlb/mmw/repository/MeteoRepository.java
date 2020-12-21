/**
 * 
 */
package it.abmlb.mmw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.abmlb.mmw.model.Sample;

/**
 * @author matteolorenzo & agnese
 *
 * Interfaccia che descrive e permette di gestire il nostro archivio dati.
 * @extends CrudRepository, interfaccia che fornisce funzionalità sofisticate
 * Create Read Update Delete per la classe (@Entity) che si sta utilizzando
 * @Repository annotazione che indica che tale classe è un repository,
 * che astrae i metodi di accesso e archiviazione dei dati.
 * 
 */
@Repository
public interface MeteoRepository extends CrudRepository<Sample, Long> {
	
	/**
	 * Metodo di query specifico per interrogare il repository per avere dati riguardo
	 * la città richiesta nell'intervallo di tempo specificato
	 */
	@Query(value = "SELECT * FROM Meteo WHERE CITY_NAME = :cityName AND EPOCH >= :start AND EPOCH <= :stop", nativeQuery = true)
	List<Sample> findSamples(@Param("cityName") String cityName,
								@Param("start") Long start,
								@Param("stop") Long stop);

}
