package business;

import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Version;
import com.restfb.exception.devicetoken.FacebookDeviceTokenCodeExpiredException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenDeclinedException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenPendingException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenSlowdownException;
import com.restfb.scope.ExtendedPermissions;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.scope.UserDataPermissions;
import com.restfb.types.DeviceCode;
import com.restfb.types.Notification;
import com.restfb.types.Post;
import com.restfb.types.User;

import exceptions.FacebookNotConfiguredException;

public class FacebookManager {
	
	private static FacebookManager instance;
	
	private Boolean pollForToken=false;
	private AccessToken accessToken;
	private ScopeBuilder scope;
	private DefaultFacebookClient client;
	private String appId;
	private String accessTokenCode;
	private DeviceCode deviceCode;
	

	@SuppressWarnings("deprecation")
	private FacebookManager() {
		scope = new ScopeBuilder();
		//scope.addPermission(UserDataPermissions.USER_ABOUT_ME);
		scope.addPermission(ExtendedPermissions.MANAGE_NOTIFICATIONS);
		loadProperties();
	}
	
	public static FacebookManager getInstance(){
		if(instance==null){
			instance=new FacebookManager();
		}
		return instance;
	}
	
	
	public void startDeviceTokenTransaction(){
		client = new DefaultFacebookClient(Version.VERSION_2_2);
		deviceCode = client.fetchDeviceCode("1842984585947331", scope);
		pollForToken=true;
	}
	
	public void pollForToken(){
		
		while(pollForToken){
		try {
			pollForToken=false;
			accessToken = client.obtainDeviceAccessToken(appId, deviceCode.getCode());
		} catch (FacebookDeviceTokenCodeExpiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FacebookDeviceTokenPendingException e) {
			System.out.println("Token pending...");
			pollForToken=true;
			try {
				Thread.sleep(deviceCode.getInterval()*1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (FacebookDeviceTokenDeclinedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FacebookDeviceTokenSlowdownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		accessTokenCode=accessToken.getAccessToken();
		PropertiesManager.getInstance().setFacebookAccessToken(accessTokenCode);
		System.out.println(accessToken.toString());
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}
	
	private void loadProperties(){
		appId=PropertiesManager.getInstance().getFacebookAppId();
		accessTokenCode=PropertiesManager.getInstance().getFacebookAccessToken();
	}
	
	public String getVerificationUri() {
		return deviceCode.getVerificationUri();
	}

	public String getUserCode() {
		return deviceCode.getUserCode();
	}
	
	public void connect() throws FacebookNotConfiguredException{
		if(accessTokenCode==null){
			throw new FacebookNotConfiguredException();
		}
		client = new DefaultFacebookClient(accessTokenCode, Version.VERSION_2_2);
		System.out.println("Facebook connected");
	}
	

	public String getAboutMe(){
		return client.fetchObject("me", User.class).getName();
	}
	
	public String getLogOutUrl(){
		return client.getLogoutUrl(null);
	}
	
	public static void main(String[] args) throws Exception{ //test purpose
		FacebookManager.getInstance().connect();
		
		Connection<Notification> myNots = FacebookManager.getInstance().getClient().fetchConnection("me/notifications", Notification.class);
		
		System.out.println(myNots.getData().size());
		System.out.println(myNots.getData().get(0).getTitle());
	}
	
	public DefaultFacebookClient getClient(){
		return client;
	}
	
	
}
