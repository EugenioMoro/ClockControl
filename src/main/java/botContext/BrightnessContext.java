package botContext;

import org.telegram.telegrambots.api.objects.Update;

import arduinoComm.Commands;
import arduinoComm.HighLevelComm;
import bot.MessageSender;
import model.BotUser;

public class BrightnessContext implements ContextInterface{


	private final static int ASKFORVALUE=0;
	private final static int PROCESSVALUE=1;
	
	private int stage=0;
	private BotUser user;
	private Thread worker;
	
	public BrightnessContext(BotUser user) {
		this.user=user;
	}

	@Override
	public void work(Update update) {
		final Update u = update;
		switch (stage){
		case ASKFORVALUE:{
			worker = new Thread(new Runnable() {

				@Override
				public void run() {
					askForValue(u);
				}
			});
			break;
		}
		case PROCESSVALUE:{
			worker = new Thread(new Runnable() {
				
				@Override
				public void run() {
					processValue(u);
				}
			});
			break;
		}
		}
		worker.start();
	}
	
	@Override
	public void abort() {
		user.setCanReply(true);
		user.setCurrentContext(null);
	}
	
	private void askForValue(Update update){
		MessageSender.simpleSend("Insert a value from 1 to 15", update);
		stage++;
		user.setCanReply(true);
	}
	
	private void processValue(Update update){
		user.setCanReply(false);
		String r = update.getMessage().getText();
		int v=16;
		Boolean parsed=true;
		try{
			v = Integer.parseInt(r);
		} catch (NumberFormatException e){
			parsed=false;
		}
		if(parsed && v>0 && v<16){
			//HighLevelComm.getInstance().appendString("~b"+v);
			HighLevelComm.getInstance().appendCommand(Commands.brightness(v));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			MessageSender.simpleSend("Brightness set", update);
			abort();
			return;
		}
		MessageSender.simpleSend("Please insert a proper value", update);
		user.setCanReply(false);
	}
	
}
