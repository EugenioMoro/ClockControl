package arduinoComm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SerialComm implements SerialPortEventListener{

private static SerialComm instance;
	
private BufferedReader input;
private OutputStream output;

private SerialPort serialPort;

private static final int TIME_OUT = 2000;
private static final int DATA_RATE = 115200;

public static SerialComm getInstance(){
	if (instance==null){
		instance = new SerialComm();
		instance.initialize();
	}
	return instance;
}

public void initialize() {
	CommPortIdentifier portId = null;
	@SuppressWarnings("rawtypes")
	Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
	PortPicker pp = new PortPicker();

	//First, Find an instance of serial port as set in PORT_NAMES.
	while (portEnum.hasMoreElements()) {
		CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			if (pp.isValid(currPortId)) {
				portId = currPortId;
				break;
			}
	}
	if (portId == null) {
		System.out.println("Could not find COM port.");
		return;
	}

	try {
		// open serial port, and use class name for the appName.
		serialPort = (SerialPort) portId.open(this.getClass().getName(),
				TIME_OUT);

		// set port parameters
		serialPort.setSerialPortParams(DATA_RATE,
				SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);

		// binds the streams
		input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		output = serialPort.getOutputStream();

		// add event listeners
		//serialPort.addEventListener(this);
		serialPort.addEventListener(HighLevelComm.getInstance());
		serialPort.notifyOnDataAvailable(true);
	} catch (Exception e) {
		System.err.println(e.toString());
	}
}

/**
 * This should be called when you stop using the port.
 * This will prevent port locking on platforms like Linux.
 */
public synchronized void close() {
	if (serialPort != null) {
		serialPort.removeEventListener();
		serialPort.close();
	}
}

/**
 * Handle an event on the serial port. Read the data and print it.
 */
public synchronized void serialEvent(SerialPortEvent oEvent) {
	if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
		try {
			String inputLine=input.readLine();
			System.out.println(inputLine);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}
	// Ignore all the other eventTypes, but you should consider the other ones.
}



 synchronized void writeString(String s){
	try {
		output.write(s.getBytes("ASCII"));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

 synchronized void writeBytes(byte[] b){
	try {
		output.write(b);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

 synchronized void writeByte(byte[] b, int position){
		try {
			output.write(b, position, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


