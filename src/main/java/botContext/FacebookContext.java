package botContext;

import org.telegram.telegrambots.api.objects.Update;

import bot.MessageSender;
import business.FacebookManager;
import model.BotUser;

public class FacebookContext implements ContextInterface {

	private static final int CHECK_AUTH=0;
	//private static final int ASK_FOR_AUTH=1;

	
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
		case CHECK_AUTH:{
			worker=new Thread(new Runnable() {
				
				@Override
				public void run() {
					checkAuth(u);
				}
			});
			break;
		}
		}
		worker.start();
	}
	
	private void checkAuth(Update update){
		if(FacebookManager.getInstance().getAccessToken()!=null){
			MessageSender.simpleSend("Facebook is connected", update);
			abort();
			return;
		}
		FacebookManager.getInstance().obtainDeviceAccessToken();
		MessageSender.simpleSend("You need to authorize this app to connect to your facebook account. Please visit "+
		FacebookManager.getInstance().getVerificationUri()+
		" and insert this code: "+
		FacebookManager.getInstance().getUserCode(), update);
		FacebookManager.getInstance().pollForToken();
		
		MessageSender.simpleSend("Done", update);
		abort();
	}

}
