package arduinoComm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import business.Session;
import exceptions.SerialNotConnectedException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import model.Command;

/*
 * This class acts as flow control for sending strings with length>63
 */

public class HighLevelComm implements SerialPortEventListener{
	
	private static HighLevelComm instance;
	private Boolean isWorking=false;
	private Vector<String> stringVector = new Vector<String>();
	private Lock lock = new ReentrantLock();
	private Condition newAppend = lock.newCondition();
	private Condition write = lock.newCondition();
	private Boolean canTransfer=true;
	private Boolean tooLong=false;
	private int tooLongCount=0;
	

	
	private Thread worker = new Thread(new Runnable() {
		
		@Override
		public void run() {
			while(true){
				processStringsTask();
			}
		}
	});
	

	public static HighLevelComm getInstance(){
		if(instance==null){
			instance=new HighLevelComm();
		}
		return instance;
	}
	
	@Override
	public void serialEvent(SerialPortEvent se) {
		if (se.getEventType() == SerialPortEvent.DATA_AVAILABLE){
			SerialPort sp = (SerialPort) se.getSource();
			lock.lock();
			try {
				BufferedReader input = new BufferedReader(new InputStreamReader(sp.getInputStream()));
				String line = input.readLine();
				System.out.println("Recived: " + line);
				if(line.equals("c")){
					canTransfer=true;
					write.signalAll();
				}
			} catch (IOException e) {
				//e.printStackTrace();
				Session.currentSession().onClessidraDisconnected();
			} finally {lock.unlock();}
			
		}
		
	}
	
	public void appendString(String s){  //recursive
		lock.lock();
		try{
			if(s.length()>60){
				canTransfer=false;
				tooLong=true;
				tooLongCount++;
				this.stringVector.addElement(s.substring(0, 60));
				appendString(s.substring(60));
			} else {
				this.stringVector.addElement(s);
			}
			newAppend.signal();
		} finally {
			lock.unlock();
			startWorker();
		}
	}
	
	private void processStringsTask(){
		lock.lock();
		try {
			while(stringVector.isEmpty()){
				newAppend.await();
			}
			sendString(stringVector.remove(0));
			if(tooLong){
				canTransfer=false;
				if(tooLongCount==0){
					tooLong=false;
					canTransfer=true;
				} else {
					tooLongCount--;
				}
				while(tooLong && !canTransfer){
				write.await();
			}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	
	public void startWorker(){
		if (!isWorking){
			this.worker.start();
			isWorking=true;
		}
	}
	
	
	private void sendString(String s){
		System.out.println("Sending: "+s);
		try {
			SerialComm.getInstance().writeString(s);
		} catch (SerialNotConnectedException e) {
			stringVector.clear();
		}
	}
	
	public void appendCommand(Command command){
		appendString(command.toString());
	}
	
	public Boolean appendStringControlled(String s){
		if(!stringVector.isEmpty()){
			return false;
		}
		appendString(s);
		return true;
	}
	
	public Boolean isBusy(){
		return !stringVector.isEmpty();
	}
	
	public synchronized Thread getWorker(){
		return worker;
	}
}
	
