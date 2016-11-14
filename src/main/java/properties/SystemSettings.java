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
	
}
