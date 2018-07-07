package chatserver;

import chat.Chatter;

public class UserInfo {

	private String name;
	private Chatter chatter;
	
	public UserInfo(String name,Chatter chatter){
		setName(name);
		setChtter(chatter);
	}

	private void setChtter(Chatter chatter) {
		this.chatter = chatter;
		
	}

	public Chatter getChatter(){
		return chatter;
	}
	
	private void setName(String name) {
		this.name = name;
		
	}
	public String getName(){
		return name;
	}
}
