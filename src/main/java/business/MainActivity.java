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
		
		System.out.println(s);

		//testing serial comm
		
		//init
//		SerialComm.getInstance().initialize();
//		
//		String s="01234567890123456789012345678901234567890123456789fiojrgoifjq34fpij23opdij409rvjefpoiwednopin";
//		s.charAt(0);
//		
//		SerialComm.getInstance().writeBytes(s.getBytes("ASCII"));
		SerialComm.getInstance();
		Thread.sleep(5000);
		//FlowControl.getInstance().appendString("");
		//FlowControl.getInstance().appendString("colacchio sbollo");
		//Thread.sleep(1000);
		CommandSender.getInstance().setTimeAsNow();
		FlowControl.getInstance().appendString("~n");
		Thread.sleep(1000);
		FlowControl.getInstance().appendString(s);
		
		
		Thread.sleep(10000);
		SerialComm.getInstance().close();
		System.exit(0);
		

		

		
}
}
