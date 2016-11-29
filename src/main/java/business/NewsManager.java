package business;

import arduinoComm.Commands;
import arduinoComm.HighLevelComm;
import model.Feed;
import model.FeedMessage;

public class NewsManager extends ScheduledTaskPrototype{
	
	private static NewsManager instance;
	
	private RSSFeedParser parser;
	private Feed feed;
	private String feedUrl="http://www.ansa.it/sito/ansait_rss.xml";
	
	private NewsManager() {
		super.setInitialDelay(1);
		super.setDelayInterval(30);
		super.setCommand(getRunnableTask());
	}
	
	public static NewsManager getInstance(){
		if (instance == null){
			instance = new NewsManager();
		}
		return instance;
	}
	
	private void parseFeed(){
		parser = new RSSFeedParser(feedUrl);
		feed = parser.readFeed();
	}
	
	public void printNews(){
		HighLevelComm.getInstance().appendCommand(Commands.news());
		parseFeed();
		feed.getMessages().size();
		String s="";
		for (FeedMessage message : feed.getMessages()) {
			s=s+"----"+message.getTitle();		
		}
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HighLevelComm.getInstance().appendString(s);
	}
	
	private Runnable getRunnableTask(){
		return new Runnable() {
			
			@Override
			public void run() {
				while(HighLevelComm.getInstance().isBusy()){
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				printNews();
			}
		};
	}

}
