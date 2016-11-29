package business;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Version;
import com.restfb.exception.devicetoken.FacebookDeviceTokenCodeExpiredException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenDeclinedException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenPendingException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenSlowdownException;
import com.restfb.scope.ScopeBuilder;
import com.restfb.scope.UserDataPermissions;
import com.restfb.types.DeviceCode;

import properties.PropertiesManager;

public class FacebookManager {
	
	private static FacebookManager instance;
	
	private Boolean pollForToken=false;
	private AccessToken accessToken;
	private ScopeBuilder scope;
	private DefaultFacebookClient client;
	private String appId;
	private String accessTokenCode;
	private DeviceCode deviceCode;
	

	private FacebookManager() {
		scope = new ScopeBuilder();
		scope.addPermission(UserDataPermissions.USER_ABOUT_ME);
		loadProperties();
	}
	
	public static FacebookManager getInstance(){
		if(instance==null){
			instance=new FacebookManager();
		}
		return instance;
	}
	
	
	public void obtainDeviceAccessToken(){
		client = new DefaultFacebookClient(Version.VERSION_2_3);
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
	
	private void connect(){
		
	}

	public String getVerificationUri() {
		return deviceCode.getVerificationUri();
	}

	public String getUserCode() {
		return deviceCode.getUserCode();
	}
	
}
