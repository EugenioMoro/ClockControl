/**
 * 
 */
package arduinoComm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * @author Eugenio
 *
 */
public class PortPicker implements SerialPortEventListener{

	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 115200;
	private SerialPort serialPort;
	@SuppressWarnings("unused")
	private BufferedReader input;
	private OutputStream output;
	private Boolean pingRecived=false;
	
	
	@Override
	public void serialEvent(SerialPortEvent se) {
		if (se.getEventType() == SerialPortEvent.DATA_AVAILABLE){
			SerialPort sp = (SerialPort) se.getSource();
			try {
				BufferedReader input = new BufferedReader(new InputStreamReader(sp.getInputStream()));
				String line = input.readLine();
				if(line.equals("p")){
					this.pingRecived=true;
				}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
	
	public Boolean isValid(CommPortIdentifier id){
		System.out.println("Opening port:");
		if(id.getPortType()==CommPortIdentifier.PORT_SERIAL){
			
			try{ //open connection
				openPortByIdentifier(id);
			} catch (Exception e){
				System.out.println("Exception in opening port");
				return false;
			}
			//try and ping
			try{
				String s = "~p";
				output.write(s.getBytes("ASCII"));
				System.out.println("Pinged, waiting for response...");
				Thread.sleep(2000);
			} catch (Exception e){
				System.out.println("Exception in sending");
				return false;
			} finally{
				serialPort.close();
			}
			if(pingRecived){
				System.out.println("Ping recived");
				return true;
			} else {return false;}
		}
		return false;
	}
	
	private void openPortByIdentifier(CommPortIdentifier id) throws Exception{
		System.out.println("Trying new connection...");
		serialPort = (SerialPort) id.open(this.getClass().getName(), TIME_OUT);
		serialPort.setSerialPortParams(DATA_RATE,
				SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
		input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		output = serialPort.getOutputStream();
		Thread.sleep(2000);
		serialPort.addEventListener(this);
		serialPort.notifyOnDataAvailable(true);
		System.out.println("Opened connction to new port: " + serialPort.getName());
	}
	
	
	

}
