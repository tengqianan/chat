package chat;

import java.rmi.RemoteException;

import chatserver.UserInfo;

public interface Chatter extends java.rmi.Remote{
	//通知用户加入
	public void receiveEnter(String name,Chatter chatter,boolean hasEntered) throws RemoteException;

	//通知用户离开聊天室
	public void receivaExti(String name) throws RemoteException;
	
	//用户发言
	public void receiveChat(String name,String message) throws RemoteException;
	
	//私聊
	public void receiveWhisper(String name ,String message) throws RemoteException;
	
	//通知服务器停止
	public void serverStop() throws RemoteException;

	
}
