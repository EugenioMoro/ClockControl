package business;

import com.sun.jmx.snmp.tasks.ThreadService;

import arduinoComm.CommandSender;
import arduinoComm.FlowControl;
import arduinoComm.SerialComm;

public class MainActivity {

	public static void main(String[] args) throws Exception {

//		RSSFeedParser parser = new RSSFeedParser(
//				"http://www.ansa.it/sito/ansait_rss.xml");
//		Feed feed = parser.readFeed();
//		System.out.println(feed);
//		feed.getMessages().size();
//		String s="";
//		for (FeedMessage message : feed.getMessages()) {
//			s=s+"----"+message.getTitle();		
//			}
//		
//		System.out.println(s);

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
		CommandSender.getInstance().setBrightness(15);
		CommandSender.getInstance().setBrightness(10);
		CommandSender.getInstance().setBrightness(1);
		
		
		Thread.sleep(10000);
		SerialComm.getInstance().close();
		System.exit(0);
		

		

		
}
}
