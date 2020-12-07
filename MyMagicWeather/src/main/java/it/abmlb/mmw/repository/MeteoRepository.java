/**
 * 
 */
package it.abmlb.mmw.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.abmlb.mmw.model.Meteo;

/**
 * @author matteolorenzo
 *
 * Descrizione dell'interfaccia qui
 *
 */
@Repository
public interface MeteoRepository extends CrudRepository<Meteo, Long> {

}
