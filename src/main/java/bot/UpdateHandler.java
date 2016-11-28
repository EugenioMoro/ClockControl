package bot;

import java.util.ArrayList;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import model.BotUser;
import properties.PropertiesManager;

public class UpdateHandler extends TelegramLongPollingBot {
	
	private static ArrayList<BotUser> users = new ArrayList<BotUser>();
	private static UpdateHandler instance;
	
	public static UpdateHandler getInstance(){
		if(instance==null){
			instance = new UpdateHandler();
		}
		return instance;
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()){
			int i=0;
			BotUser user=null;
			//if new set as new user
			if(users.isEmpty()){
				user = new BotUser();
				user.setId(update.getMessage().getChatId());
				users.add(user);
			} else {
				for(i=0; i<users.size(); i++){
					if(users.get(i).getId()==update.getMessage().getChatId()){
						user=users.get(i);
						break;
					}
				}
				if(i>users.size()){
					user = new BotUser();
					user.setId(update.getMessage().getChatId());
					users.add(user);
				}
			} //end of new user
			if(!user.getCanReply()){ //check if reply is expected
				System.out.println("reply not expected");
			} else {
				if(user.getCurrentContext()==null){ //if no context: if is command then process else print message
					System.out.println("No context active, interpreting...");
					if(update.getMessage().isCommand()){
						BotCommandProcesser.process(update, user);
					} else {
						BotCommandProcesser.printString(update);
					}
				}
			}
			
			if(user.getCurrentContext()!=null){
				if(update.getMessage().getText().equals("/cancel")){
					user.getCurrentContext().abort();
				} else {
					user.getCurrentContext().work(update);
					System.out.println("update dispatched");
				}
			}
			
			
			
		}
		
	}

	@Override
	public String getBotToken() {
		return PropertiesManager.getInstance().getBotToken();
	}
	
	public static BotUser getUserById(long id){
		for(int i=0; i<users.size(); i--){
			if(users.get(i).getId()==id)
				return users.get(i);
		}
		return null;
	}
	
	public static BotUser getUserByUpdate(Update update){
		return getUserById(update.getMessage().getChatId());
	}

}
