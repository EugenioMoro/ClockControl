package business;

import exceptions.ClessidraNotFoundException;
import exceptions.SerialNotConnectedException;

public class Logger {
	
	public static void ExceptionRaised(Exception e){
		System.out.println("Excepion: " + e.getClass().getName());
		switch (e.getClass().getName()){
		case ClessidraNotFoundException.CLASS_NAME:
			notifyClessidraNotFound();
			break;
		case SerialNotConnectedException.CLASS_NAME:
			notifyClessidraNotConnected();
			break;
		}
	}
	
	public static void notifyClessidraNotFound(){
		System.out.println("Clessidra not found");
	}
	
	public static void notifyClessidraNotConnected(){
		System.out.println("Clessidra not connected");
	}
	
	
	
}
