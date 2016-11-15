package properties;

import org.aeonbits.owner.ConfigFactory;

public class SystemSettings {

	private static SystemConfig scf = ConfigFactory.create(SystemConfig.class);
	
	public static String getWeatherApiKey(){
		return scf.weatherApiKey();
	}
	
	public static String getWeatherCity(){
		return scf.weatherCity();
	}
	
	public static String getMailUsername(){
		return scf.mailUsername();
	}
	
	public static char[] getMailPassword(){
		return scf.mailPassword().toCharArray();
	}
	

}
