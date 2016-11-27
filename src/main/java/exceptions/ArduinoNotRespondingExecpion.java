/**
 * 
 */
package exceptions;

/**
 * @author Eugenio
 *
 */
public class ArduinoNotRespondingExecpion extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getMessage(){
		return "no ping";
	}
}
