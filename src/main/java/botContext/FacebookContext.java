package botContext;

import org.telegram.telegrambots.api.objects.Update;

import bot.MessageSender;
import business.FacebookManager;
import exceptions.FacebookNotConfiguredException;
import model.BotUser;

public class FacebookContext implements ContextInterface {

	private static final int CHECK_CONNECTION=0;
	private static final int ASK_FOR_AUTH=1;
	private static final int SWITCH_MENU=2;

	
	private int stage=0;
	private BotUser user;
	private Thread worker;
	
	public FacebookContext(BotUser user) {
		this.user=user;
	}
	
	@Override
	public void abort() {
		user.setCanReply(true);
		user.setCurrentContext(null);
	}

	@Override
	public void work(Update update) {
		final Update u = update;
		switch (stage){
		case CHECK_CONNECTION:{
			worker=new Thread(new Runnable() {
				
				@Override
				public void run() {
					checkConnection(u);
				}
			});
			break;
		}
		case ASK_FOR_AUTH:{
			worker=new Thread(new Runnable() {
				
				@Override
				public void run() {
					askForAuth(u);
				}
			});
		}
		case SWITCH_MENU:{
			worker=new Thread(new Runnable() {
				
				@Override
				public void run() {
					switchMenu(u);
				}
			});
		}
		}
		worker.start();
	}
	
	private void checkConnection(Update update){
		try {
			FacebookManager.getInstance().connect();
		} catch (FacebookNotConfiguredException e) {
			System.out.println("facebook not configured");
			stage++;
			askForAuth(update);
			return;
		}
		sendMenu(update);
		stage=SWITCH_MENU;
	}
	
	private void askForAuth(Update update){
		FacebookManager.getInstance().startDeviceTokenTransaction();
		
		MessageSender.simpleSend("You need to authorize this app to connect to your facebook account. Please visit "+
		FacebookManager.getInstance().getVerificationUri()+
		" and insert this code: "+
		FacebookManager.getInstance().getUserCode(), update);
		MessageSender.simpleSend("/cancel", update);
		
		
		FacebookManager.getInstance().pollForToken();
		MessageSender.simpleSend("All done, your Facebook account is now associated with Clessidra", update);
		stage=SWITCH_MENU;
	}
	
	private void sendMenu(Update u){
		MessageSender.simpleSend("Chose an option: /aboutMe\n/logout from my account\n/cancel", u);
	}
	
	private void switchMenu(Update u){
		switch(u.getMessage().getText()){
		case "/aboutMe":
			MessageSender.simpleSend(FacebookManager.getInstance().getAboutMe(), u);
			sendMenu(u);
			break;
		case "/logout":
			MessageSender.simpleSend("Open this page: "+FacebookManager.getInstance().getLogOutUrl(), u);
		}
	}

}
