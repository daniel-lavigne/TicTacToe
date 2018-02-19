package serverPac;

import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;

import messagePac.Message;
/**
 * 
 * @author Daniel Lavigne (664978)
 * Class Description: Manages the communication between two players.
 *
 */
public class ClientHandler extends Thread implements Observer{
	
	private int count1 = 1;
	private int count2 = 1;
	public ObjectOutputStream oos1;
	public ObjectOutputStream oos2;

	/**
	 * Default constructor for the ClientHandler class
	 */
	public ClientHandler(){
		
	}
	/**
	 * Receives two sockets from the server and establishes communication with two instances of the InputListener class.
	 * @param cs1 - the socket for the first player to connect to the server.
	 * @param cs2 - the socket for the second player to connect to the server.
	 */
	public ClientHandler(Socket cs1, Socket cs2){
		
		try {
			
			System.out.println("clienthandler reached");
			oos1 = new ObjectOutputStream(cs1.getOutputStream());
			InputListener in1 = new InputListener(cs1, 1);
			Thread t1 = new Thread(in1);
			t1.start();
			
			oos2 = new ObjectOutputStream(cs2.getOutputStream());
			InputListener in2 = new InputListener(cs2, 2);
			Thread t2 = new Thread(in2);
			t2.start();
			
			in1.addObserver(this);
			in2.addObserver(this);
		
		} 
		
		catch (IOException e) {
			System.out.println("Client Handler Error");
			//e.printStackTrace();
		}
		
	}
	/**
	 * The update method managing updates from the InputListener (observable) class. Sends the incoming Message objects to the correct player.
	 */
	@Override
	public synchronized void update(Observable o, Object arg) {
		
		InputListener in = (InputListener)o;
		Message m = in.getM();
		if(m.getPlayerNum() == 1){
			if(count1 == 1){
				//m.setTurn(false);
				m.setUsername("Game Server");
				m.setMessage("Welcome you are player 2");
				count1++;
			}
			try {
				oos2.reset();
				oos2.writeObject(m);
			} catch (IOException e) {
				System.out.println("Output Stream 2 error");
				//e.printStackTrace();
			}
		}
		else if(m.getPlayerNum() == 2){
			if(count2 == 1){
				m.setTurn(true);
				m.setUsername("Game Server");
				m.setMessage("Welcome you are player 1");
				count2++;
			}
			try {
				oos1.reset();
				oos1.writeObject(m);
			} catch (IOException e) {
				System.out.println("Output Stream 1 error");
				//e.printStackTrace();
			}
		}
		
	}

}
