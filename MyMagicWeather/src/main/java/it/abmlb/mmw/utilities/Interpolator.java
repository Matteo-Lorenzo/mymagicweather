/**
 * 
 */
package it.abmlb.mmw.utilities;

import java.util.TreeSet;

import it.abmlb.mmw.exceptions.MmwInterpolationOutOfBoundsException;
import it.abmlb.mmw.model.Point;

/**
 * 
 * Classe che implementa i metodi di interpolazione lineare utilizzati per il
 * calcolo degli andamenti
 * @extends MmwUtils
 * 
 * @author matteolorenzo&agnese
 */
public class Interpolator extends MmwUtils{

	/**
	 * Struttura dati interna necessaria per contenere i dati campionati
	 */
	private TreeSet<Point> punti;
	
	/**
	 * Costruttore di default
	 */
	public Interpolator() {
		punti = new TreeSet<Point>();
	}
	
	/**
	 * Metodo per aggiungere dati al TreeSet
	 * @param punto Punto contenente i dati campionati
	 */
	public void addSample(Point punto) {
		this.punti.add(punto); //non gestisco il ritorno booleano di add volutamente,
		//semplicemente non sono accettati valori duplicati poichè da quelli 
		//non si otterrebbe una funzione
	}
	
	/**
	 * Metodo che permette di calcolare l'ordinata di un nuovo punto tramite
	 * interpolazione lineare
	 * @param punto Punto di cui ritornare i dati, calcolandoli tramite l'interpolazione
	 * @throws MmwInterpolationOutOfBoundsException 
	 */
	public Point valueFor(Point punto) throws MmwInterpolationOutOfBoundsException { 
		
		if((punto.getX().longValue() < punti.first().getX().longValue())||
				(punto.getX().longValue() > punti.last().getX().longValue())) {
			
			throw new MmwInterpolationOutOfBoundsException("Fuori range");
		}
		if(punto.getX().equals(punti.first().getX())) {
			
			return punti.first();
		}
		if(punto.getX().equals(punti.last().getX())) {
			
			return punti.last();
		}
		
		Point prec = punti.floor(punto);
		Point succ = punti.ceiling(punto);
		if (prec.equals(succ)) {//se precedente e successivo sono LO STESSO PUNTO(oggetto) vuol dire
								//che il punto da interpolare coincide con un campione reale presente nel Set
			punto.setY(round(succ.getY()));
			//lo score di qualità è massimo perchè i punti coincidono
			punto.setScore(1.0);
		} else { // se ho effettivamente due estremi diversi dell'intervallo 
					//faccio l'interpolazione
			Double m = (succ.getY()-prec.getY())/(succ.getX()-prec.getX()); //coeffic. angolare
			punto.setY(round(prec.getY()+m*(punto.getX()-prec.getX())));
			
			punto.setScore(round(this.score(prec.getX(), succ.getX(), punto.getX())));
		}
		return punto;
	}
	
	/**
	 * Metodo ausiliario privato che assegna lo score di qualità
	 * @param inf Estremo inferiore dell'intervallo in cui è contenuto il punto interpolato
	 * @param sup Estremo supariore dell'intervallo in cui è contenuto il punto interpolato
	 * @param val Ascissa del punto interpolato
	 * 
	 */
	private Double score(Long inf, Long sup, Long val) {
		//seleziono la distanza minima in valore assoluto del punto interpolato dagli estremi dell'intervallo
		Double temp = (double) Math.min(Math.abs(val-inf), Math.abs(val-sup));
		//divido la distanza trovata sopra per la lunghezza dell'intervallo
		//lo score di qualità sarà 0.5 se il punto si trova esattamente a metà dell'intervallo
		//o maggiore di 0.5 proporzionalmente alla vicinanza a uno dei due estremi
		Double score = 1 - temp/Math.abs(sup-inf);
		return score;
	}
	
}

