package model;

public class Command {
	
	private String command;
	private String data="";
	
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public Command(String command, String data) {
		super();
		this.command = command;
		this.data = data;
	}
	
	public String toString(){
		return this.command+this.data;
	}
	
	
	
}
