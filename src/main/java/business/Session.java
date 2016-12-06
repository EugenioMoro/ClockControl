package business;

import java.util.ArrayList;

import arduinoComm.SerialComm;
import bot.MessageSender;
import model.BotUser;

public class Session {
	
	private static Session currentSession;
	
	private ArrayList<BotUser> users = new ArrayList<BotUser>();
	private Boolean telegramAvailable=true;
	
	
	public static Session currentSession(){
		if (currentSession==null){
			currentSession = new Session();
		}
		return currentSession;
	}


	public ArrayList<BotUser> getUsers() {
		return users;
	}
	
	public void startScheduledTasks(){
		NewsManager.getInstance().startScheduling();
		WeatherManager.getInstance().startScheduling();
	}
	
	public void stopScheduling(){
		NewsManager.getInstance().stopScheduling();
		WeatherManager.getInstance().stopScheduling();
	}
	
	public Boolean isSerialAvailable(){
		return SerialComm.getInstance().getIsConnected();
	}
	
	public void onClessidraDisconnected(){
		stopScheduling();
		MessageSender.clessidraDisconnectedNotification();
		SerialComm.getInstance().close();
		SerialComm.getInstance().startSearchForClessidra();
	}
	
	public void onClessidraConnected(){
		System.out.println("Clessidra connected");
		MessageSender.clessidraConnectedNotification();
		startScheduledTasks();
	}
	
	public void onTelegramDisconnected(){
		System.out.println("Telegram exception");
		this.telegramAvailable=false;
	}
	
	public Boolean isTelegramAvailable(){
		return telegramAvailable;
	}
	
	
	
}
