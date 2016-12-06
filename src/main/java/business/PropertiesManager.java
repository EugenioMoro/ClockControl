package business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
	
	private static PropertiesManager instance;
	
	private static String FILE_NAME = "config";
	private Properties properties;
	
	public static PropertiesManager getInstance(){
		if(instance==null){
			instance = new PropertiesManager();
		}
		return instance;
	}
	
	private PropertiesManager() {
		initialize();
	}
	
	private void initialize(){
		load();
	}
	
	private void load(){
		FileInputStream in = null;
		properties = new Properties();
		try { //open
			in = new FileInputStream(FILE_NAME);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		try { //load
			properties.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public String getWeatherApiKey(){
		return properties.getProperty("weatherApiKey");
	}
	
	public String getWeatherCity(){
		return properties.getProperty("weatherCity", "Lecce");
	}
	
	public String getBotToken(){
		return properties.getProperty("botToken");
	}
	
	public String getFacebookAppId(){
		return properties.getProperty("facebookAppId");
	}
	
	public String getFacebookAccessToken(){
		return properties.getProperty("facebookAccessToken");
	}
	
	public void setFacebookAccessToken(String accessToken){
		properties.setProperty("facebookAccessToken", accessToken);
		saveReload();
	}
	
	private void saveReload(){
		FileOutputStream fo=null;
		try {
			fo = new FileOutputStream(FILE_NAME);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			properties.store(fo, "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		load();
	}
	

}
