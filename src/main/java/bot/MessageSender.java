package bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import business.Logger;
import model.BotUser;

public class MessageSender {
	
	private static SendMessage m = new SendMessage();
	
	public static void simpleSend(String text, long chatId){
		m.setChatId(chatId);
		m.setText(text);
		try {
			UpdateHandler.getInstance().sendMessage(m);
		} catch (TelegramApiException e) {
			Logger.ExceptionRaised(e);
		}
	}
	
	public static void simpleSend(String text, BotUser user){
		simpleSend(text, user.getId());
	}
	
	public static void simpleSend(String text, Update update){
		simpleSend(text, update.getMessage().getChatId());
	}

}
