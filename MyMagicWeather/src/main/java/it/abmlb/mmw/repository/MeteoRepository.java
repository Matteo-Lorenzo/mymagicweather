/**
 * 
 */
package it.abmlb.mmw.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.abmlb.mmw.model.Meteo;

/**
 * @author matteolorenzo & brugl
 *
 * Interfaccia che descrive il nostro mmwStore, cioè l'archivio dati
 *
 */
@Repository
public interface MeteoRepository extends CrudRepository<Meteo, Long> {

}
