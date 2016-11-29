package business;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

import com.restfb.types.User;

import arduinoComm.HighLevelComm;
import arduinoComm.SerialComm;
import bot.UpdateHandler;

public class MainActivity {

	public static void main(String[] args) throws Exception {

		SerialComm.getInstance();
		Thread.sleep(2000);
		HighLevelComm.getInstance().appendString("~b"+1);
		
		ApiContextInitializer.init(); 
		TelegramBotsApi botApi = new TelegramBotsApi();
		botApi.registerBot(UpdateHandler.getInstance());
		
		User u= new User();
	
		
}
}
