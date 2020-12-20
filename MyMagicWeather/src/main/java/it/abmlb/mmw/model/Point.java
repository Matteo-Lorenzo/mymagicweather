/**
 * 
 */
package it.abmlb.mmw.model;

/**
 * @author matteolorenzo & agnese
 * 
 * Classe che descrive il modello dei punti utilizzati dall'interpolatore lineare
 * per ricavare gli andamenti temporali di una specifca grandezza
 * @implements Comparable 
 *
 */
public class Point implements Comparable<Point>{
	
	/**
	 * @param x Ascissa che rappresenta la coordinata temporale del punto
	 * @param y Ordinata che rappresenta la grandezza da interpolare
	 * @param score Livello di qualità [0.50, 1.0] del punto interpolato
	 * 
	 */
	private Long x;
	private Double y;
	private Double score;
	
	/**
	 * @param x Ascissa che rappresenta la coordinata temporale del punto
	 * @param y Ordinata che rappresenta la grandezza da interpolare
	 */
	public Point(Long x, Double y) {
		super();
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * @return the x
	 */
	public Long getX() {
		return x;
	}
	
	/**
	 * @param x the x to set
	 */
	public void setX(Long x) {
		this.x = x;
	}
	
	/**
	 * @return the y
	 */
	public Double getY() {
		return y;
	}
	
	/**
	 * @param y the y to set
	 */
	public void setY(Double y) {
		this.y = y;
	}
	
	/**
	 * @return the score
	 */
	public Double getScore() {
		return score;
	}
	
	/**
	 * @param score the score to set
	 */
	public void setScore(Double score) {
		this.score = score;
	}
	
	/**
	 * Overriding del metodo compareTo dell'interfaccia Comparable che serve per
	 * definire una relazione d'ordine tra i vari punti.
	 * Questa relazione serve per poter inserire in maniera ordinata i punti nel
	 * Treeset utilizzato dall'interpolatore
	 */
	@Override
	public int compareTo(Point o) {
		if (this.getX()==o.getX()) 
			return 0;
		if (this.getX()<o.getX()) {
			return -1;
		} else {
			return 1;
		}
		
	}
	
	/**
	 * Overriding del metodo toString di Object utilizzato per fare dei test di funzionamento
	 */
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

}
