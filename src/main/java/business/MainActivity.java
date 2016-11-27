package business;

import arduinoComm.CommandSender;
import arduinoComm.FlowControl;
import arduinoComm.SerialComm;
import model.Feed;
import model.FeedMessage;

public class MainActivity {

	public static void main(String[] args) throws Exception {

		RSSFeedParser parser = new RSSFeedParser(
				"http://www.ansa.it/sito/ansait_rss.xml");
		Feed feed = parser.readFeed();
		System.out.println(feed);
		feed.getMessages().size();
		String s="";
		for (FeedMessage message : feed.getMessages()) {
			s=s+"----"+message.getTitle();		
			}
		SerialComm.getInstance();
		Thread.sleep(2000);
		
		FlowControl.getInstance().appendString("~n");
		Thread.sleep(500);
		FlowControl.getInstance().appendString(s);
		
		

		
}
}
