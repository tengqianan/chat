package chatserver;

import java.util.EventListener;

public interface ChatServerListener extends EventListener{
	public void serverEvent(ChatServerEvent evt);

}
