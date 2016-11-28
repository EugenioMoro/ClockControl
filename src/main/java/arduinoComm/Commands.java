package arduinoComm;

import java.util.Date;

import model.Command;

public class Commands {
	
	private Commands() {
		// TODO Auto-generated constructor stub
	}
	
	public static Command brightness(int value){
		return new Command("~b", String.valueOf(value));
	}
	
	public static Command time(){
		java.util.Date date=new Date();
	    String data = String.valueOf((date.getTime()/1000L+3600));
	    return new Command("~t", data);
	}
	
	public static Command news(){
		return new Command("~n", "");
	}
	
}
