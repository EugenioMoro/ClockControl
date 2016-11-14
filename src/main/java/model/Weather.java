/**
 * 
 */
package model;

/**
 * @author Eugenio
 *
 */
public class Weather {
	
	private String temperature;
	private String humidity;
	private String pressure;
	private String WeatherType;
	private String WheaterDescription;
	
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getPressure() {
		return pressure;
	}
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	public String getWeatherType() {
		return WeatherType;
	}
	public void setWeatherType(String weatherType) {
		WeatherType = weatherType;
	}
	public String getWheaterDescription() {
		return WheaterDescription;
	}
	public void setWheaterDescription(String wheaterDescription) {
		WheaterDescription = wheaterDescription;
	}
	
	@Override
	public String toString(){
		return temperature + pressure + humidity + WeatherType + WheaterDescription;
	}

}
