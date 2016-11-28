package properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import business.Logger;

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
			Logger.ExceptionRaised(e);
			return;
		}
		try { //load
			properties.load(in);
		} catch (IOException e) {
			Logger.ExceptionRaised(e);
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
	

}
