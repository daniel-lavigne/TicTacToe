/**
 * 
 */
package serverPac;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import messagePac.Message;

/**
 * @author Daniel Lavigne 664978
 * Class Description: Listens for incoming communications and relays the information to the observer.
 *
 */
public class InputListener extends Observable implements Runnable{
	
	public Socket s = null;
	public int player = 0;
	//public ObjectInputStream ois = null;
	Message m = new Message();
	/**
	 * Default constructor for the InputListener class
	 */
	public InputListener(){
		
	}
	/**
	 * Constructor allowing another class to becoming an observer for InputListener.
	 * @param s - The socket for the observer
	 * @param o - The observer
	 */
	public InputListener(Socket s, Observer o){
		super();
		this.s = s;
		addObserver(o);
	}
	/**
	 * The constructor used by the ClientHandler class to instantiate separate instances of the InputListener class for each player 
	 * @param s - the player socket passed through the server and ClientHandler
	 * @param player - the player number assigned by the ClientHandler Class
	 */
	public InputListener(Socket s, int player){
		
		super();
		this.s = s;
		this.player = player;
	}
	/**
	 * Main run method for the InputListener, runs a infinite loop waiting for new objects coming through the input stream
	 */
	public void run(){
		
		try {
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			while(true){
				Message m = (Message)ois.readObject();
				if(player != 0){
					m.setPlayerNum(player);
				}
				setM(m);
				setChanged();
				notifyObservers();
			}
				
		} 
		catch (IOException e) {
				System.out.println("InputListener Closed");
				//e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
	}
	/**
	 * Get method allowing the observer to access updates
	 * @return m - a Message object
	 */
	public Message getM() {
		return m;
	}
	/**
	 * Allows the local Message object to be updated using newly arrived data. 
	 * @param m - the local Message object
	 */
	public void setM(Message m) {
		this.m = m;
	}
	
	
		

}
