package business;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledTaskPrototype {
	
	private  ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	private  ScheduledFuture<?> future;
	private  int initialDelay;
	private  int taskDelay;
	private  Runnable command;
	private  Boolean isRunning=false;
	
	protected void setCommand(Runnable command) {
		this.command = command;
	}

	protected void setDelayInterval(int minutes) {
		this.taskDelay=minutes;
	}

	protected void setInitialDelay(int minutes){
		this.initialDelay=minutes;
	}

	public void stopScheduling() {
		future.cancel(false);
		isRunning=false;
	}


	public void startScheduling() {
		future=executor.scheduleAtFixedRate(command, (long) initialDelay, (long) taskDelay, TimeUnit.MINUTES);
		isRunning=true;
	}
	

	public Boolean isRunning(){
		return isRunning;
	}

}
