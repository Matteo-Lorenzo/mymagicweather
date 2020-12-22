/**
 * 
 */
package it.abmlb.mmw.utilities;

import java.util.Vector;

import it.abmlb.mmw.exceptions.MmwStatsException;

/**
 * 
 * Classe che implementa i metodi utilizzati per il calcolo delle Statistiche
 * @extends MmwUtils
 * 
 * @author matteolorenzo&agnese
 */
public class StatCalculator extends MmwUtils{
	
	/**
	 * Massimo valore assunto dalla grandezza nell'intervallo considerato
	 */
	private Double max;
	/**
	 * Minimo valore assunto dalla grandezza nell'intervallo considerato
	 */
	private Double min;
	/**
	 * Contatore della quantità di campioni utilizzati
	 */
	private Integer sampleNum;
	/**
	 * Accumulatore dei valori dei campioni utilizzati
	 */
	private Double accumulator;
	/**
	 * Vettore contenente i campioni utilizzati
	 */
	private Vector<Double> samples;
	
	/**
	 * Costruttore di default
	 */
	public StatCalculator() {
		this.max = -Double.MAX_VALUE;
		this.min = Double.MAX_VALUE;
		this.sampleNum = 0;
		this.accumulator = 0.0;
		this.samples = new Vector<Double>();
	}
	
	/**
	 * Metodo per aggiungere dati
	 * Esegue diverse operazioni on the fly per assegnare i corretti valori a
	 * max, min, sampleNum, e accumulator
	 * @param sample Campione contenente i dati
	 */
	public void addSample(Double sample) {
		this.sampleNum++;
		this.accumulator += sample;
		if (sample > this.max)
			this.max = sample;
		if (sample < this.min)
			this.min = sample;
		this.samples.add(sample);
	}

	/**
	 * @return the max
	 * @throws MmwStatsException 
	 */
	public Double getMax() throws MmwStatsException {
		if(max==-Double.MAX_VALUE)
			throw new MmwStatsException("Assenza di campioni");
		else
			return max;
	}

	/**
	 * @return the min
	 * @throws MmwStatsException 
	 */
	public Double getMin() throws MmwStatsException {
		if(min==Double.MAX_VALUE)
			throw new MmwStatsException("Assenza di campioni");
		else
			return min;
	}

	/**
	 * @return the variance
	 * @throws MmwStatsException 
	 */
	public Double getVariance() throws MmwStatsException {
		if(max==-Double.MAX_VALUE)
			throw new MmwStatsException("Assenza di campioni");
		else {
			Double avg = this.getAverage();
			Double temp = 0.0;
			for (Double sample : samples) {
				temp += Math.pow((sample - avg), 2);
			}
			return round(temp/this.sampleNum);
		}
	}
	
	/**
	 * @return the average
	 * @throws MmwStatsException 
	 */
	public Double getAverage() throws MmwStatsException {
		if(max==-Double.MAX_VALUE)
			throw new MmwStatsException("Assenza di campioni");
		else
			return round(accumulator/sampleNum);
	}

}
