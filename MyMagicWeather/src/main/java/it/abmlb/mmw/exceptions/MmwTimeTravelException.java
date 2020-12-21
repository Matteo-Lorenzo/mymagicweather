/**
 * 
 */
package it.abmlb.mmw.exceptions;

/**
 * @author matteolorenzo & agnese
 *
 */
public class MmwTimeTravelException extends Exception {

	
	private static final long serialVersionUID = 1L;

	
	public MmwTimeTravelException() {
		super();
	}

	/**
	 * @param message Description of the Exception
	 */
	public MmwTimeTravelException(String message) {
		super(message);
	}

}
