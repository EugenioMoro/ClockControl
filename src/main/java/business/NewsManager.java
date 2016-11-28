package business;

import arduinoComm.Commands;
import arduinoComm.HighLevelComm;
import model.Feed;
import model.FeedMessage;

public class NewsManager {
	
	private static NewsManager instance;
	
	private RSSFeedParser parser;
	private Feed feed;
	private String feedUrl="http://www.ansa.it/sito/ansait_rss.xml";
	
	private NewsManager() {
		
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

}
