package properties;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources("file:./SystemConfig.properties")
public interface SystemConfig extends Config{
	
	String weatherApiKey();
	
	@DefaultValue("Lecce")
	String weatherCity();

	String mailUsername();
	
	String mailPassword();
}
