package business;

public class Logger {
	
	public static void ExceptionRaised(Exception e){
		System.out.println(e.getStackTrace());
	}
	
}
