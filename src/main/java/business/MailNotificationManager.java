/**
 * 
 */
package business;

/**
 * @author Eugenio
 *
 */
public class MailNotificationManager {
	
	private static MailNotificationManager instance;

	private MailNotificationManager() {
	}

	public static MailNotificationManager getInstance() {
		if (null == instance) {
			instance = new MailNotificationManager();
		}
		return instance;
	}

}
