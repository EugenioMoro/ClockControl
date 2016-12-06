package bot;

import java.util.ArrayList;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import arduinoComm.SerialComm;
import business.Session;
import model.BotUser;

public class MessageSender {
	
	private static SendMessage m = new SendMessage();
	
	public static void simpleSend(String text, long chatId){
		m.setChatId(chatId);
		m.setText(text);
		try {
			UpdateHandler.getInstance().sendMessage(m);
		} catch (TelegramApiException e) {
			Session.currentSession().onTelegramDisconnected();
			e.printStackTrace();
		}
	}
	
	public static void simpleSend(String text, BotUser user){
		simpleSend(text, user.getId());
	}
	
	public static void simpleSend(String text, Update update){
		simpleSend(text, update.getMessage().getChatId());
	}
	
	public static void clessidraNotConnectedMessage(Update update){
	  simpleSend("Please, connect clessidra", update);
	}
	
	public static void clessidraDisconnectedNotification(){
		ArrayList<BotUser> users = Session.currentSession().getUsers();
		if(users.isEmpty()){
			return;
		}
		for(int i=0; i<users.size(); i++){
			simpleSend("Clessidra has been disconnected", users.get(i));
		}
	}
	
	public static void welcomeMessage(BotUser user){
		String s;
		if(SerialComm.getInstance().getIsConnected()){
			s="Welcome! Click here-> /help";
		} else {
			s="Welcome, please connect clessidra";
		}
		simpleSend(s, user);
	}
	
	public static void clessidraConnectedNotification(){
		ArrayList<BotUser> users = Session.currentSession().getUsers();
		if(users.isEmpty())
			return;
		for(int i=0; i<users.size(); i++){
			simpleSend("Clessidra found and connected", users.get(i));
		}
	}

}
