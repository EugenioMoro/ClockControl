package properties;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Reloadable;

@Sources("file:./SystemConfig.properties")
public interface SystemConfig extends Config, Reloadable{
	
	String weatherApiKey();
	
	@DefaultValue("Lecce")
	String weatherCity();

	String mailUsername();
	
	String mailPassword();
	
	String botToken();
	
	void setTest(String s);
}
