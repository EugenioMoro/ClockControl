package business;

public class Session {
	
	private static Session currentSession;
	
	
	public static Session currentSession(){
		if (currentSession==null){
			currentSession = new Session();
		}
		return currentSession;
	}
	
	
}
