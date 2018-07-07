 package chatserver;

import java.util.EventObject;

public class ChatServerEvent extends EventObject{

	String message;
	public ChatServerEvent(Object src,String message) {
		super(src);
		setMessage(message);
		
	}
	private void setMessage(String message) {
		this.message = message;
		
	}
	public String getMessage(){
		return message;
	}

}
