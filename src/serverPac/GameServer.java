/**
 * 
 */
package serverPac;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;

/**
 * @author Daniel Lavigne (664978)
 *Class Description: A server which handles the communication between multiple game players.
 *
 */
public class GameServer extends Observable implements Runnable{

	public ObjectOutputStream oos = null;
	private String msg = null;
	/**
	 * Constructor for the GameServer class allowing it to be observed by another class.
	 * @param o - the class to be added as an observer.
	 */
	public GameServer(Observer o){
		addObserver(o);
	}
	/**
	 * Starts the server as an infinite loop. When player connect, they are added to an array list. Once the list contains two players, the list is passed to the ClientHandler class. The list is then emptied.
	 * Sends text updates to the server GUI. 
	 * 	 
	 **/
	public void run() {
		
		ServerSocket ss = null;
		ArrayList<Socket> socketList = new ArrayList<Socket>(2);
		
		try {
			
			ss = new ServerSocket(5555);
			
		} catch (IOException e) {
			
			setMsg("Could not start server, Socket alread in use \n");
			setChanged();
			notifyObservers();
		
		}
		while(true){
			
			try {
				
				Socket cs = ss.accept();
				setMsg("New Client Accepted \n");
				setChanged();
				notifyObservers();
				socketList.add(cs);
				
				if(socketList.size() == 2)
				{
					ClientHandler ch = new ClientHandler(socketList.get(0),socketList.get(1));
					setMsg("2 clients passed to handler \n");
					setChanged();
					notifyObservers();
					ch.start();
					socketList.clear();
				}
	
			} 
			catch (IOException e) {
				setMsg("Server Error");
				setChanged();
				notifyObservers();
			} 
		}

	}
	/**
	 * A get method so that the observing class can receive new messages from the server class.
	 * @return msg - the message String sent to the observer.
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * A set method to set the value of msg.
	 * @param msg - a message which can be viewed by an observer.
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
