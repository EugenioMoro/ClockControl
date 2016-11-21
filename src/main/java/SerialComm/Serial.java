/**
 * 
 */
package SerialComm;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * @author Eugenio
 *
 */
public class Serial {
	public static void main(String[] args) throws InterruptedException{
		SerialPort serialPort = new SerialPort("COM4");
		try {
		    serialPort.openPort();
		    Thread.sleep(2000);

		    serialPort.setParams(SerialPort.BAUDRATE_9600,
		                         SerialPort.DATABITS_8,
		                         SerialPort.STOPBITS_1,
		                         SerialPort.PARITY_NONE);

		    //serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
		                                  //SerialPort.FLOWCONTROL_RTSCTS_OUT);

		    serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);

		    serialPort.writeString("Hurrah!");
		    serialPort.closePort();
		}
		catch (SerialPortException ex) {
		    System.out.println("There are an error on writing string to port т: " + ex);
		}
	}private static class PortReader implements SerialPortEventListener {
		
	

	    @Override
	    public void serialEvent(SerialPortEvent event) {
	    	System.out.println("dio");
	        if(event.isRXCHAR() && event.getEventValue() > 0) {
	            //String receivedData = serialPort.readString(event.getEventValue());
				System.out.println("Received response: ");
	        }
}
	}
	
	}
