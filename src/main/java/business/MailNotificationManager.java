/**
 * 
 */
package business;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.googlecode.gmail4j.GmailClient;
import com.googlecode.gmail4j.GmailConnection;
import com.googlecode.gmail4j.GmailMessage;
import com.googlecode.gmail4j.http.HttpGmailConnection;
import com.googlecode.gmail4j.rss.RssGmailClient;

/**
 * @author Eugenio
 *
 */
public class MailNotificationManager {
	
	private static MailNotificationManager instance;
	
	private Date lastMessageDate=null;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private MailNotificationManager() {
	}

	public static MailNotificationManager getInstance() {
		if (null == instance) {
			instance = new MailNotificationManager();
		}
		return instance;
	}
	
	public void startPeriodicCheck(int initialDelay){
		Runnable task = new Runnable() {
			
			public void run() {
				System.out.println("Checking for new mails");
				taskToSchedule();
			}
		};
		scheduler.scheduleAtFixedRate(task, initialDelay, 5, TimeUnit.MINUTES);
	}
	
	private List<GmailMessage> fetchUnreadMessages(){
		String username=null;//=SystemSettings.getMailUsername();
		char pwd[]=null;// = SystemSettings.getMailPassword();
		
		GmailClient client = new RssGmailClient();
		GmailConnection connection = new HttpGmailConnection(username, pwd);
		client.setConnection(connection);
		return client.getUnreadMessages();
	}
	
	private void taskToSchedule(){
		List<GmailMessage> unreadList = fetchUnreadMessages();
		int totalUnreadMessagesCount = unreadList.size();
		
		//delete from list each message that has been already considered
		if (lastMessageDate!=null){
			for(int i=unreadList.size()-1; i>=0; i--){
				if (unreadList.get(i).getSendDate().before(lastMessageDate) || unreadList.get(i).getSendDate().equals(lastMessageDate)){
					unreadList.remove(i);
				}
			}
		} else { lastMessageDate=unreadList.get(0).getSendDate(); }

		if(unreadList.size()!=0){
			System.out.println(prettyPrinter(unreadList, totalUnreadMessagesCount));
		}

	}

	private String prettyPrinter(List<GmailMessage> messageList, int unreadCount){
		String s = "";
		
		if (messageList.size()!=0){
			s="You have " + messageList.size() + "new messages...   ";
			for (int i=0; i<messageList.size(); i++){
				s=s + "From " + messageList.get(i).getFrom().getEmail() + " Subject: " + messageList.get(i).getSubject() + "   ";
			}
		}
		
		s=s+"You have " + unreadCount;
		if (unreadCount==20){
			s=s+"+";
		}
		s=s+" unread messages";
		
		return s;
	}
	
	public void test(){
		taskToSchedule();
	}
}
