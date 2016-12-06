package arduinoComm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import business.Session;
import exceptions.SerialNotConnectedException;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class SerialComm{

private static SerialComm instance;
	
private OutputStream output;

private SerialPort serialPort;
private Boolean isConnected=false;
private Thread connectionWorker;


private static final int TIME_OUT = 2000;
private static final int DATA_RATE = 115200;

public static SerialComm getInstance(){
	if (instance==null){
		instance = new SerialComm();
		instance.initialize();
	}
	return instance;
}

private void initialize() {
	connectionWorker=new Thread(getConnectionTask());
	connectionWorker.start();
}

private void connect(){
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
		output = serialPort.getOutputStream();

		// add event listeners
		//serialPort.addEventListener(this);
		serialPort.addEventListener(HighLevelComm.getInstance());
		serialPort.notifyOnDataAvailable(true);
		isConnected=true;
		Session.currentSession().onClessidraConnected();
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
		isConnected=false;
	}
}

/**
 * Handle an event on the serial port. Read the data and print it.
 */



 synchronized void writeString(String s) throws SerialNotConnectedException{
	if(!isConnected){
		throw new SerialNotConnectedException();
	}
	try {
		output.write(s.getBytes("ASCII"));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

 synchronized void writeBytes(byte[] b) throws SerialNotConnectedException{
		if(!isConnected){
			throw new SerialNotConnectedException();
		}
	try {
		output.write(b);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

 synchronized void writeByte(byte[] b, int position) throws SerialNotConnectedException{
		if(!isConnected){
			throw new SerialNotConnectedException();
		}
		try {
			output.write(b, position, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
 private Runnable getConnectionTask(){
	 return new Runnable() {

		 @Override
		 public void run() {
			 while(true){
				 if(!isConnected){
					 connect();
				 }
				 try {
					 Thread.sleep(2000);
				 } catch (InterruptedException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 }
			 }
		 }
	 };
 }

public Boolean getIsConnected() {
	return isConnected;
}

public void startSearchForClessidra(){
	this.connectionWorker.start();
}

 
	
}


