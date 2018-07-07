package chat;

import java.rmi.RemoteException;

public interface ChatServer extends java.rmi.Remote{

	//注册新的聊天用户
	public void login(String name,Chatter chatter) throws RemoteException;
	
	//用户退出
	public void logout(String name) throws RemoteException;
	
	//消息发送
	public void chat(String name,String message) throws RemoteException;
}
