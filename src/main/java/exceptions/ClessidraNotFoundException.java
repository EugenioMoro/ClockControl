/**
 * 
 */
package exceptions;

/**
 * @author Eugenio
 *
 */
public class ClessidraNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String CLASS_NAME = "exceptions.ClessidraNotFoundException";
	
	public String getMessage(){
		return "no ping";
	}
}
