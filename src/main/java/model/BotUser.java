package model;

import botContext.ContextInterface;

public class BotUser {
	
	private long id;
	private ContextInterface currentContext=null;
	private Boolean canReply=true;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ContextInterface getCurrentContext() {
		return currentContext;
	}
	public void setCurrentContext(ContextInterface currentContext) {
		this.currentContext = currentContext;
	}
	public Boolean getCanReply() {
		return canReply;
	}
	public void setCanReply(Boolean canReply) {
		this.canReply = canReply;
	}
	@Override
	public String toString() {
		return "BotUser [id=" + id + ", currentContext=" + currentContext + ", canReply=" + canReply + "]";
	}
	
	

	
	

}
