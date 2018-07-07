package chatsever_Main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import chatserver.ChatServerEvent;
import chatserver.ChatServerImpl;
import chatserver.ChatServerListener;
import chatsever_Main.Main.StarServerAction;
import chatsever_Main.Main.StopServerAction;

public class Main extends JFrame implements ChatServerListener{
	
	ChatServerImpl server = ChatServerImpl.getInstance();
	JTextArea textArea;
	JMenuBar menuBar;
	JToolBar toolBar;
	StarServerAction startAction = new StarServerAction();
	StopServerAction stopAction = new StopServerAction();
	
	class StopServerAction extends AbstractAction{
		public StopServerAction(){
			super("停止");
			putValue(Action.SMALL_ICON,new ImageIcon(getClass().getResource("")));
			putValue(Action.SHORT_DESCRIPTION,"停止聊天服务器");
			putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke("cintrol O"));
			this.setEnabled(false);
		}
		

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				
				server.stop();
				server.removeListener(Main.this);
				startAction.setEnabled(true);
				this.setEnabled(false);
			} catch (Exception e1) {
		
				textArea.append("服务器停止错误\n");
				e1.printStackTrace();
				return;
			}
			
		}

	}

	class StarServerAction extends AbstractAction{
		public StarServerAction(){
			super("启动");
			putValue(Action.SMALL_ICON,new ImageIcon(getClass().getResource("")));
			putValue(Action.SHORT_DESCRIPTION,"启动聊天服务器");
			putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke("cintrol A"));
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
	try {
		        server.removeListener(Main.this);
		        textArea.setText("");
				server.start();
				startAction.setEnabled(true);
				this.setEnabled(false);
			} catch (Exception e2) {
		
				textArea.append("服务器启动错误\n");
				server.removeListener(Main.this);
				e2.printStackTrace();
				return;
			}
			
		}

	}




	public static void main(String[] args) {
		Main main = new Main();
		main.show();

	}
	
	public Main(){
		super("聊天-服务器");
		setSize(300,500);
		layoutComponents();
	}

	private void layoutComponents() {
		setupMenu();
		setupToolBar();
		textArea = new JTextArea();
		textArea.setSize(200,300);
		textArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter(){
			public void awindowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}

	private void setupToolBar() {
		toolBar = new JToolBar();
		JButton button = null;

		getContentPane().add(toolBar, BorderLayout.NORTH);
		
	}




	private void setupMenu() {
		menuBar = new JMenuBar();
		JMenuItem startServer = new JMenuItem(startAction);
		JMenuItem stopServer = new JMenuItem(stopAction);
		JMenuItem exit = new JMenuItem("离开");
		exit.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent evt){
				exit();
			}
		});
		JMenu server = new JMenu("服务器");
		server.add(startServer);
		server.add(stopServer);
		server.add(exit);
		menuBar.add(server);
		setJMenuBar(menuBar);
	}

	@Override
	public void serverEvent(ChatServerEvent evt) {
		textArea.append(evt.getMessage()+"\n");
		
	}
	private void exit(){
		try{
		server.stop();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.exit(0);
	}

}
