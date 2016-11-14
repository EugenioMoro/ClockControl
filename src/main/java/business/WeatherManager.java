package business;

import org.openweathermap.api.UrlConnectionWeatherClient;
import org.openweathermap.api.WeatherClient;
import org.openweathermap.api.model.MainParameters;
import org.openweathermap.api.model.currentweather.CurrentWeather;
import org.openweathermap.api.query.Language;
import org.openweathermap.api.query.QueryBuilderPicker;
import org.openweathermap.api.query.ResponseFormat;
import org.openweathermap.api.query.Type;
import org.openweathermap.api.query.UnitFormat;
import org.openweathermap.api.query.currentweather.CurrentWeatherOneLocationQuery;

import model.Weather;
import properties.SystemSettings;

public class WeatherManager {
	
	private static WeatherManager instance;
	
	private WeatherClient client;
	private CurrentWeather currentWeather;
	
	public static WeatherManager getInstance(){
		if (instance == null){
			instance=new WeatherManager();
		}
		return instance;
	}
	
	public WeatherManager(){
		client = new UrlConnectionWeatherClient(SystemSettings.getWeatherApiKey());
	}
	
	private void updateWeatherData(){
		CurrentWeatherOneLocationQuery currentWeatherOneLocationQuery = QueryBuilderPicker.pick()
                .currentWeather()                   // get current weather
                .oneLocation()                      // for one location
                .byCityName(SystemSettings.getWeatherCity())              // for Kharkiv city
                .countryCode("IT")                  // in Ukraine
                .type(Type.ACCURATE)                // with Accurate search
                .language(Language.ITALIAN)         // in English language
                .responseFormat(ResponseFormat.JSON)// with JSON response format
                .unitFormat(UnitFormat.METRIC)      // in metric units
                .build();
        		currentWeather = client.getCurrentWeather(currentWeatherOneLocationQuery);
	}
	
	public Weather getWeather(){
		updateWeatherData();
		Weather w = new Weather();
		MainParameters mp = currentWeather.getMainParameters();
		
		w.setHumidity(String.valueOf(mp.getHumidity()));
		w.setPressure(String.valueOf(mp.getPressure()));
		w.setTemperature(String.valueOf(mp.getTemperature()));
		w.setWeatherType(currentWeather.getWeather().get(0).getMain());
		w.setWheaterDescription(currentWeather.getWeather().get(0).getDescription());
		
		return w;
	}
	
	
}
