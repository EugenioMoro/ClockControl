/**
 * 
 */
package arduinoComm;

/**
 * @author Eugenio
 *
 */
public class CommandModel implements Runnable {
	
	public static final String SET_TIME="~t";
	public static final String SET_BRIGHTNESS="~b";
	
	private String command;
	private String data="";
	

	
	
	public CommandModel(String command, String data) {
		this.command = command;
		this.data = data;
	}




	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FlowControl.getInstance().appendString(command+data);
	}
	


}
