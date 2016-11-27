/**
 * 
 */
package arduinoComm;

import java.util.Date;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Eugenio
 *
 *COMMAND IS COMPOSED OF A ~ THEN CHAR THAT IDENTIFIES THE OPERATION AND A STRING OF ARBITRARY LENGTH WHICH IS THE DATA
 *
 *----- t expects seconds from 1970 and sets it as current time
 *
 *----- b expects a value from 1 to 15 and sets it as brightness
 *
 */
public class CommandSender {
	
	private static CommandSender instance;
	
	private Lock lock = new ReentrantLock();
	private Condition newCommand = lock.newCondition();
	private Vector<CommandModel> commandRuns = new Vector<>();
	private Thread worker = new Thread(new Runnable() {
		
		@Override
		public void run() {
			while(true){
				processCommand();
			}
		}
	});
	
	/**
	 * 
	 */
	public CommandSender() {
		this.worker.start();
	}
	
	public static CommandSender getInstance(){
		if (instance==null){
			instance = new CommandSender();
		}
		return instance;
	}
	
	
	public void setTimeAsNow(){
		java.util.Date date=new Date();
	    String data = String.valueOf((date.getTime()/1000L+3600));
	    appendCommand(new CommandModel(CommandModel.SET_TIME, data));
	}
	
	public void setBrightness(int b){
		if(b>=1 && b<=15){
			appendCommand(new CommandModel(CommandModel.SET_BRIGHTNESS, String.valueOf(b)));
		}
	}
	
	public void announceNews(){
		appendCommand(new CommandModel(CommandModel.ANNOUNCE_NEWS, ""));
	}
	
	private void appendCommand(CommandModel command){
		lock.lock();
		try{
			commandRuns.addElement(command);
			newCommand.signal();
		} finally {
			lock.unlock();
			}
	}
	
	private void processCommand(){
		lock.lock();
		try{
			while(commandRuns.isEmpty()){
				newCommand.await();
			}
			commandRuns.remove(0).run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	
}
