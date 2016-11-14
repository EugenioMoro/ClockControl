package business;

import java.util.List;

import com.googlecode.gmail4j.GmailClient;
import com.googlecode.gmail4j.GmailConnection;
import com.googlecode.gmail4j.GmailMessage;
import com.googlecode.gmail4j.http.HttpGmailConnection;
import com.googlecode.gmail4j.rss.RssGmailClient;

public class MainActivity {

	public static void main(String[] args) {
		String username="librariumsoftware@gmail.com";
		String p = "marramoro";
		char pwd[] = p.toCharArray();
		
		
		System.out.println("Clock Control\n");
		GmailClient client = new RssGmailClient();
		GmailConnection connection = new HttpGmailConnection(username, pwd);
		client.setConnection(connection);
		final List<GmailMessage> messages = client.getUnreadMessages();
		for (GmailMessage message : messages) {
		    System.out.println(message);
		}
	}

}
