package business;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

import arduinoComm.SerialComm;
import bot.UpdateHandler;

public class MainActivity {

	public static void main(String[] args) throws Exception {

		SerialComm.getInstance(); //first call to getInstance() initializes the class
		Thread.sleep(2000);
		
		ApiContextInitializer.init();  // telegram bot set up
		TelegramBotsApi botApi = new TelegramBotsApi();
		botApi.registerBot(UpdateHandler.getInstance());
		
		
	}
}
