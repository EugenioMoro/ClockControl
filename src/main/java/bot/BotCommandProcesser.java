package bot;

import org.telegram.telegrambots.api.objects.Update;

import arduinoComm.Commands;
import arduinoComm.HighLevelComm;
import arduinoComm.SerialComm;
import botContext.BrightnessContext;
import botContext.FacebookContext;
import business.NewsManager;
import model.BotUser;

public class BotCommandProcesser {
	
	private static final String HELP="/help";
	private static final String BRIGHTNESS="/brightness";
	private static final String TIME="/settime";
	private static final String NEWS="/getNews";
	private static final String FACEBOOK="/facebook";
	
	public static void process(Update update, BotUser user){
		switch(update.getMessage().getText()){
		case HELP:{
			sendHelp(update);
			break;
		}
		case BRIGHTNESS:{
			setBrightness(update);
			break;
		}
		case TIME:
		{
			setTime(update);
			break;
		}
		case NEWS:{
			sendNews(update);
			break;
		}
		case FACEBOOK:{
			facebook(update);
			break;
		}
		}
	}
	
	private static void sendHelp(Update update){
		MessageSender.simpleSend("help", update);
	}
	
	private static void setBrightness(Update update){
		if(!canAppend(update))
			return;
		System.out.println("Brightness command");
		UpdateHandler.getUserByUpdate(update).setCurrentContext(new BrightnessContext(UpdateHandler.getUserByUpdate(update)));
		UpdateHandler.getUserByUpdate(update).setCanReply(false);
	}
	
	private static void setTime(Update update){
		if(!canAppend(update))
			return;
		HighLevelComm.getInstance().appendCommand(Commands.time());
		MessageSender.simpleSend("Time set", update);
	}
	
	private static void sendNews(Update update){
		if(!canAppend(update))
			return;
		NewsManager.getInstance().printNews();
		MessageSender.simpleSend("Sending news", update);
	}
	
	public static void printString(Update update){
		if(!canAppend(update))
			return;
		HighLevelComm.getInstance().appendString(update.getMessage().getText());
	}
	
	private static void clockBusyMessageSend(Update update){
		MessageSender.simpleSend("Clessidra is busy right now, try later", update);
	}
	
	private static void facebook(Update update){
		UpdateHandler.getUserByUpdate(update).setCurrentContext(new FacebookContext(UpdateHandler.getUserByUpdate(update)));
		UpdateHandler.getUserByUpdate(update).setCanReply(false);
	}
	
	private static Boolean canAppend(Update update){
		if(!SerialComm.getInstance().getIsConnected()){
			MessageSender.clessidraNotConnectedMessage(update);
			return false;
		}
		if(HighLevelComm.getInstance().isBusy()){
			clockBusyMessageSend(update);
			return false;
		}
		return true;
	}
	
}
