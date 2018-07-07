package chatserver;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import chat.ChatServer;
import chat.Chatter;

public class ChatServerImpl extends java.rmi.server.UnicastRemoteObject implements ChatServer{

	static ChatServerImpl server = null;
	private final static String BINDNAME = "ChatServer";
	private final static String[] STATEMSG = new String[]{"服务器启动","服务器停止"};
	List chatters = new ArrayList();
	List listeners = new ArrayList();
	
	protected ChatServerImpl() throws java.rmi.RemoteException {
	
	}
  
	public static ChatServerImpl getInstance(){
		try {
			if(server == null){
				server = new ChatServerImpl();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return server;
	}
	
	public void start() throws Exception{
		java.rmi.Naming.rebind(BINDNAME, server);
		notifyListener(STATEMSG[0]);
	}
	//添加一个侦听者
	public void addListener(ChatServerListener listener){
		listeners.remove(listener);
	}
	
	//移除一个侦听者
	public void removeListener(ChatServerListener listener){
		listeners.remove(listener);
	}
	
	void notifyListener(String msg) {
		//通知所有的侦听者事件发生
		Iterator itr =listeners.iterator();
		ChatServerEvent evt = new ChatServerEvent(this,msg);
		while(itr.hasNext()){ 
			((ChatServerListener) itr.next()).serverEvent(evt);
		}
		
	}
	
	public void stop() throws Exception{
		notifyListener(STATEMSG[1]);
		Iterator itr = chatters.iterator();
		while(itr.hasNext()){
			UserInfo u = (UserInfo) itr.next();
			u.getChatter().serverStop();
		}
		java.rmi.Naming.unbind(BINDNAME);
	}

	//注册新的聊天用户
	public void login(String name,Chatter chatter) throws RemoteException {
		if((chatter !=null) && (name != null)){
			UserInfo u = new UserInfo(name,chatter);
			notifyListener(u.getName()+"进入聊天室");
			Iterator itr = chatters.iterator();
			while (itr.hasNext()){
				UserInfo u2 = (UserInfo) itr.next();
				u2.getChatter().receiveEnter(name, chatter, false);
				chatter.receiveEnter(u2.getName(), u2.getChatter(), true);
			}
			chatters.add(u);
		}
	}
	
	//用户退出
	public void logout(String name) throws RemoteException {
		if(name == null){
			System.out.println("null name on logout:cannot remote chatter");
			return ; 
		}
		UserInfo u_gone = null;
		Iterator itr = null;
		
		synchronized(chatters){
			for(int i=0;i<chatters.size();i++){
				UserInfo u = (UserInfo) chatters.get(i);
				if(u.getName().equals(name)){
					notifyListener(name+"离开聊天室");
					u_gone = u;
					chatters.remove(i);
					itr = chatters.iterator();
					break;
				}
				
			}
		}
		if(u_gone == null || itr == null){
			System.out.println("no user by name of"+name+"found:not removing chatter");
			return ;
		}
		while(itr.hasNext()){
			UserInfo u = (UserInfo) itr.next();
			u.getChatter().receivaExti(name);
		}
	}
	
	//消息发送
	public void chat(String name,String message) throws RemoteException {
		Iterator itr = chatters.iterator();
		
		while(itr.hasNext()){
			UserInfo u = (UserInfo) itr.next();
			if(!name.equals(u.getName())){
				u.getChatter().receiveChat(name, message);
			}
			notifyListener(name+":"+message);
		}
	}
}
