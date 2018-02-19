package clientGUI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

import serverPac.InputListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import messagePac.Message;
/**
 * 
 * @author Daniel Lavigne (664978)
 * @version 1.0
 * Class Description: This class is used to create the main game window, and contains the methods used to send and recieve new communications from the server.
 * Observes the InputListener class
 *
 */
public class MainWindow extends JFrame implements Observer, MouseListener{

	private JPanel contentPane;
	private JTextField chatSendField;
	private JTextField txtIpAddress;
	private JTextArea chatField;
	//Game Buttons
	private JButton btnConnect = new JButton("Connect");
	private JButton btnSubmitChat = new JButton();
	private JButton btnA1 = new JButton();
	private JButton btnA2 = new JButton();
	private JButton btnA3 = new JButton();
	private JButton btnB1 = new JButton();
	private JButton btnB2 = new JButton();
	private JButton btnB3 = new JButton();
	private JButton btnC1 = new JButton();
	private JButton btnC2 = new JButton();
	private JButton btnC3 = new JButton();
	Message message = new Message();
	
	private String myMove = null;
	private String theirMove = null;
	private boolean myTurn = false;
	private String disconnectMsg = "!dIsCoNnEcT!";
	
	//Server variables
	Socket socket = null;
	public ObjectOutputStream oos = null;
	public ObjectInputStream ois = null;
	private String username = null;
	private String msg = null;
	private String ip = null;
	InputListener il = new InputListener();

	/**
	 * Create the main game window frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 718, 552);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTictactoe = new JLabel("TIC-TAC-TOE");
		lblTictactoe.setBounds(12, 13, 690, 25);
		lblTictactoe.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblTictactoe);
		
		chatSendField = new JTextField();
		chatSendField.setBounds(399, 43, 289, 30);
		contentPane.add(chatSendField);
		chatSendField.setColumns(10);
		
		btnSubmitChat.setText("Send");
		btnSubmitChat.setBounds(591, 75, 97, 25);
		btnSubmitChat.addActionListener(new gameButtonListener());
		contentPane.add(btnSubmitChat);
		
		btnConnect.setBounds(12, 469, 155, 25);
		btnConnect.addActionListener(new gameButtonListener());
		contentPane.add(btnConnect);
		
		txtIpAddress = new JTextField();
		txtIpAddress.setForeground(Color.LIGHT_GRAY);
		txtIpAddress.setText("IP Address");
		txtIpAddress.setBounds(12, 440, 200, 25);
		txtIpAddress.addMouseListener(this);
		contentPane.add(txtIpAddress);
		txtIpAddress.setColumns(10);
		
		//JButton btnA1 = new JButton();
		btnA1.setText(null);
		btnA1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnA1.setBounds(36, 75, 100, 100);
		btnA1.setEnabled(false);
		btnA1.addActionListener(new gameButtonListener());
		contentPane.add(btnA1);
		
		//JButton btnA2 = new JButton("");
		btnA2.setText(null);
		btnA2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnA2.setBounds(148, 75, 100, 100);
		btnA2.setEnabled(false);
		btnA2.addActionListener(new gameButtonListener());
		contentPane.add(btnA2);
		
		//JButton btnA3 = new JButton("");
		btnA3.setText(null);
		btnA3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnA3.setBounds(260, 75, 100, 100);
		btnA3.setEnabled(false);
		btnA3.addActionListener(new gameButtonListener());
		contentPane.add(btnA3);
		
		//JButton btnB1 = new JButton("");
		btnB1.setText(null);
		btnB1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnB1.setBounds(36, 184, 100, 100);
		btnB1.setEnabled(false);
		btnB1.addActionListener(new gameButtonListener());
		contentPane.add(btnB1);
		
		//JButton btnB2 = new JButton("");
		btnB2.setText(null);
		btnB2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnB2.setBounds(148, 184, 100, 100);
		btnB2.setEnabled(false);
		btnB2.addActionListener(new gameButtonListener());
		contentPane.add(btnB2);
		
		//JButton btnB3 = new JButton("");
		btnB3.setText(null);
		btnB3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnB3.setBounds(260, 184, 100, 100);
		btnB3.setEnabled(false);
		btnB3.addActionListener(new gameButtonListener());
		contentPane.add(btnB3);
		
		//JButton btnC1 = new JButton("");
		btnC1.setText(null);
		btnC1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnC1.setBounds(36, 297, 100, 100);
		btnC1.setEnabled(false);
		btnC1.addActionListener(new gameButtonListener());
		contentPane.add(btnC1);
		
		//JButton btnC2 = new JButton("");
		btnC2.setText(null);
		btnC2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnC2.setBounds(148, 297, 100, 100);
		btnC2.setEnabled(false);
		btnC2.addActionListener(new gameButtonListener());
		contentPane.add(btnC2);
		
		//JButton btnC3 = new JButton("");
		btnC3.setText(null);
		btnC3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnC3.setBounds(260, 297, 100, 100);
		btnC3.setEnabled(false);
		btnC3.addActionListener(new gameButtonListener());
		contentPane.add(btnC3);
		
		chatField = new JTextArea();
		chatField.setEditable(false);
		chatField.setWrapStyleWord(true);
		chatField.setLineWrap(true);
		
		JScrollPane chatScroll = new JScrollPane(chatField);
		chatScroll.setBounds(399, 105, 289, 351);
		contentPane.add(chatScroll);
		
		//chatField.setBounds(399, 105, 289, 351);
		//contentPane.add(chatField);
		
		
				
	}
	/**
	 * 
	 * The main Action Listener method for the game window GUI
	 * Records the moves made in the game, connects/disconnects to the server and sends chat messages.
	 * Also checks, win/lose/tie scenarios after each new move is made.
	 *
	 */
	private class gameButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == btnConnect)
			{
				if(txtIpAddress.getText() != null || !txtIpAddress.getText().isEmpty()){
					
					ip = txtIpAddress.getText();
				
					connect();
				}
			}
			
			
			if (e.getSource() == btnA1){
				btnA1.setText(myMove);
				btnA1.setEnabled(false);
				sendMove("A1");
			}
			
			if (e.getSource() == btnA2){
				btnA2.setText(myMove);
				btnA2.setEnabled(false);
				sendMove("A2");
			}
			
			if (e.getSource() == btnA3){
				btnA3.setText(myMove);
				btnA3.setEnabled(false);
				sendMove("A3");
			}
			
			if (e.getSource() == btnB1){
				btnB1.setText(myMove);
				btnB1.setEnabled(false);
				sendMove("B1");
			}
			
			if (e.getSource() == btnB2){
				btnB2.setText(myMove);
				btnB2.setEnabled(false);
				sendMove("B2");
			}
			
			if (e.getSource() == btnB3){
				btnB3.setText(myMove);
				btnB3.setEnabled(false);
				sendMove("B3");
			}
			
			if (e.getSource() == btnC1){
				btnC1.setText(myMove);
				btnC1.setEnabled(false);
				sendMove("C1");
			}
			
			if (e.getSource() == btnC2){
				btnC2.setText(myMove);
				btnC2.setEnabled(false);
				sendMove("C2");
			}
			
			if (e.getSource() == btnC3){
				btnC3.setText(myMove);
				btnC3.setEnabled(false);
				sendMove("C3");
			}
			
			if(e.getSource() == btnSubmitChat){
				
				msg = chatSendField.getText();
				chatSendField.setText(null);
				chatField.append(username + ":\n " + msg + "\n");
				sendMessage(msg);
				
			}
			//win conditions
			if(myMove != null && theirMove != null){
				
				if(checkWinLose()){
					
					myTurn = false;
					System.out.println("game over");
					checkTurn();
					if(newGame("You Won! \n Do you want to play again?")){
						myTurn = true;
						checkTurn();
					}
				
				
				}
				else if(checkTie()){
					
					myTurn = false;
					System.out.println("game over");
					checkTurn();
					if(newGame("Tie Game! \n Do you want to play again?")){
						myTurn = true;
						checkTurn();
					}
					
				}
			}
				
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		txtIpAddress.setText(null);
		txtIpAddress.setForeground(Color.black);
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * The update method for the Observer/Observable pattern
	 * Breaks down newly recieved Message objects in order to update the chat window.
	 * Also recieves new game moves and calls the check win/lose/tie methods
	 */
	@Override
	public synchronized void update(Observable o, Object arg) {
		
		Message m = il.getM();
		if(m.getMessage() != null){
			if(m.getMessage().equals(disconnectMsg)){
				int reply = JOptionPane.showConfirmDialog(null, "Your opponent has disconnect \n Do you wish to find a new one?", "New Game", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION){
					disconnect();
					connect();
				}
				else{
					disconnect();
				}
			}
			//chatField.setText(m.toString());
			else{
				chatField.append(m.toString() + "\n");
			}
		}
		
		if(m.getPlayerNum() == 1){
			myMove = "O";
			theirMove = "X";
		}
		
		if(m.getPlayerNum() == 2){
			myMove = "X";
			theirMove = "O";
		}
		
		if(m.getMove() != null){
		
			recieveMove(m.getMove());
			if(checkWinLose()){
				
				myTurn = false;
				//System.out.println("game over");
				checkTurn();
				newGame("You Lost \n Do you want to play again?");
			
			}
			
			else if(checkTie()){
				
				myTurn = false;
				//System.out.println("game over");
				checkTurn();
				newGame("Tie Game! \n Do you want to play again?");
				
			}
			else{
				
				//System.out.println("game still going");
				myTurn = true;
				checkTurn();
			}
		}
		
		if(m.isTurn()){
			//System.out.println("is turn");
			myTurn = true;
			checkTurn();
		}	
		
	}//end update

/*******************************************Private Methods****************************************************/
/**
 * Opens a new socket, ObjectOutputStream, prompts the user to enter their user name, connects to the server, instantiates a new instance of the InputListener class
 * and sends the initial connection message to the server.
 */
	private void connect(){
		
		try {
			
			btnA1.setText(null);
			btnA2.setText(null);
			btnA3.setText(null);
			btnB1.setText(null);
			btnB2.setText(null);
			btnB3.setText(null);
			btnC1.setText(null);
			btnC2.setText(null);
			btnC3.setText(null);
			btnA1.setBackground(null);
			btnA2.setBackground(null);
			btnA3.setBackground(null);
			btnB1.setBackground(null);
			btnB2.setBackground(null);
			btnB3.setBackground(null);
			btnC1.setBackground(null);
			btnC2.setBackground(null);
			btnC3.setBackground(null);
			socket = new Socket(ip, 5555);
			username = JOptionPane.showInputDialog("Enter user name");
			message = new Message(0,username, null, false, null);
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(message);
			il = new InputListener(socket, this);
			Thread t = new Thread(il);
			t.start();
			btnConnect.setEnabled(false);
			
		} 
		
		catch (HeadlessException | IOException e1) 
		{
			JOptionPane.showMessageDialog(null, "Could not connect to server \n" + "Please check your ip address");
			//e1.printStackTrace();
		}
	}//end connect
	/**
	 * Disconnects the client from the server by closing the ObjectOutputStream and the socket
	 */
	private void disconnect(){
		
		try {
			
			myTurn = false;
			checkTurn();
			socket.close();
			oos.close();
			btnConnect.setEnabled(true);
			
		}
		
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}//end disconnect
	
	/**
	 * Evaluates the myTurn boolean. If true, checks for empty move spaces and unlocks those that are found.
	 * If false, locks all the buttons on the game board.
	 */
	private void checkTurn(){
		
		if(myTurn){
			
			if(btnA1.getText() == null){
				btnA1.setEnabled(true);
			}
			
			if(btnA2.getText() == null){
				btnA2.setEnabled(true);
			}
			
			if(btnA3.getText()== null){
				btnA3.setEnabled(true);
			}
			
			if(btnB1.getText()== null){
				btnB1.setEnabled(true);
			}
			
			if(btnB2.getText()== null){
				btnB2.setEnabled(true);
			}
			
			if(btnB3.getText()== null){
				btnB3.setEnabled(true);
			}
			
			if(btnC1.getText()== null){
				btnC1.setEnabled(true);
			}
			
			if(btnC2.getText()== null){
				btnC2.setEnabled(true);
			}
			
			if(btnC3.getText()== null){
				btnC3.setEnabled(true);
			}
		}
		
		else if(!myTurn){
			
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
		
		}
	}//end checkTurn
	
	/**
	 * Sends a new text message through the ObjectOutput Stream using a Message object
	 * @param msg - a String containing the message being sent through the output stream
	 */
	private void sendMessage(String msg){
		message = new Message(0,username, msg, false, null);
		//txtIpAddress.setText(message.toString());
		try {
			oos.reset();
			oos.writeObject(message);
			
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null,"Connection Error: message not sent");
			System.out.println("Connection error, message not sent");
			//e1.printStackTrace();
		}
	}
	/**
	 * Send a string representing the move made by the player through the output stream.
	 * @param newMove - a two character String corresponding to a location on the game board.
	 */
	private void sendMove(String newMove){
		
		message = new Message(0,null, null, false, newMove);
		try {
			oos.reset();
			oos.writeObject(message);
			myTurn = false;
			checkTurn();
			
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null,"Connection Error: Move not sent");
			System.out.println("Connection Error: Move not sent");
			//e1.printStackTrace();
		}
		
	}
	
	/**
	 * Used by the update method to interpret a new move received from an opponent.
	 * Moves are received as a two character String and are assigned to the appropriate space.
	 * @param newMove - two character string received by the update method.
	 */
	private void recieveMove(String newMove){
		
		if(newMove.equals("A1")){
			//System.out.println("move is A1");
			btnA1.setText(theirMove);
			btnA1.setEnabled(false);
			
		}
		
		else if(newMove.equals("A2")){
			//System.out.println("move is A1");
			btnA2.setText(theirMove);
			btnA2.setEnabled(false);
			
		}
		
		else if(newMove.equals("A3")){
			//System.out.println("move is A1");
			btnA3.setText(theirMove);
			btnA3.setEnabled(false);
		
		}
		
		else if(newMove.equals("B1")){
			//System.out.println("move is A1");
			btnB1.setText(theirMove);
			btnB1.setEnabled(false);
		
		}
		
		else if(newMove.equals("B2")){
			//System.out.println("move is A1");
			btnB2.setText(theirMove);
			btnB2.setEnabled(false);
	
		}
		
		else if(newMove.equals("B3")){
			//System.out.println("move is A1");
			btnB3.setText(theirMove);
			btnB3.setEnabled(false);

		}
		
		else if(newMove.equals("C1")){
			//System.out.println("move is A1");
			btnC1.setText(theirMove);
			btnC1.setEnabled(false);

		}
		
		else if(newMove.equals("C2")){
			//System.out.println("move is A1");
			btnC2.setText(theirMove);
			btnC2.setEnabled(false);

		}
		
		else if(newMove.equals("C3")){
			//System.out.println("move is A1");
			btnC3.setText(theirMove);
			btnC3.setEnabled(false);

		}
		else{
			
		}
	}
	
	/**
	 * Check for all possible win and lose scenarios .
	 * @return boolean value, if win or lose returns true, else returns false.
	 */
	private boolean checkWinLose(){
		
		if (btnA1.getText() == myMove && btnA2.getText() == myMove && btnA3.getText() == myMove){
			
			btnA1.setBackground(Color.green);
			btnA2.setBackground(Color.green);
			btnA3.setBackground(Color.green);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		
		}//end A1,A2,A3 win
	
		if (btnB1.getText() == myMove && btnB2.getText() == myMove && btnB3.getText() == myMove){
		
			btnB1.setBackground(Color.green);
			btnB2.setBackground(Color.green);
			btnB3.setBackground(Color.green);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end B1,B2,B3
	
		if (btnC1.getText() == myMove && btnC2.getText() == myMove && btnC3.getText() == myMove){
			
			btnC1.setBackground(Color.green);
			btnC2.setBackground(Color.green);
			btnC3.setBackground(Color.green);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end C1,C2,C3
	
		if (btnA1.getText() == myMove && btnB1.getText() == myMove && btnC1.getText() == myMove){
		
			btnA1.setBackground(Color.green);
			btnB1.setBackground(Color.green);
			btnC1.setBackground(Color.green);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end A1,B1,C1
	
		if (btnA2.getText() == myMove && btnB2.getText() == myMove && btnC2.getText() == myMove){
			
			btnA2.setBackground(Color.green);
			btnB2.setBackground(Color.green);
			btnC2.setBackground(Color.green);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end C1,C2,C3
	
		if (btnA3.getText() == myMove && btnB3.getText() == myMove && btnC3.getText() == myMove){
		
			btnA3.setBackground(Color.green);
			btnB3.setBackground(Color.green);
			btnC3.setBackground(Color.green);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end C1,C2,C3
	
		if (btnA1.getText() == myMove && btnB2.getText() == myMove && btnC3.getText() == myMove){
			
			btnA1.setBackground(Color.green);
			btnB2.setBackground(Color.green);
			btnC3.setBackground(Color.green);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end C1,C2,C3
	
		if (btnC1.getText() == myMove && btnB2.getText() == myMove && btnA3.getText() == myMove){
		
			btnC1.setBackground(Color.green);
			btnB2.setBackground(Color.green);
			btnA3.setBackground(Color.green);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end C1,C2,C3
		//end win conditions
		
		if (btnA1.getText() == theirMove && btnA2.getText() == theirMove && btnA3.getText() == theirMove){
			
			btnA1.setBackground(Color.red);
			btnA2.setBackground(Color.red);
			btnA3.setBackground(Color.red);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		
		}//end A1,A2,A3 loss
	
		if (btnB1.getText() == theirMove && btnB2.getText() == theirMove && btnB3.getText() == theirMove){
		
			btnB1.setBackground(Color.red);
			btnB2.setBackground(Color.red);
			btnB3.setBackground(Color.red);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end B1,B2,B3 loss
	
		if (btnC1.getText() == theirMove && btnC2.getText() == theirMove && btnC3.getText() == theirMove){
			
			btnC1.setBackground(Color.red);
			btnC2.setBackground(Color.red);
			btnC3.setBackground(Color.red);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end C1,C2,C3 loss
	
		if (btnA1.getText() == theirMove && btnB1.getText() == theirMove && btnC1.getText() == theirMove){
		
			btnA1.setBackground(Color.red);
			btnB1.setBackground(Color.red);
			btnC1.setBackground(Color.red);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end A1,B1,C1 loss
	
		if (btnA2.getText() == theirMove && btnB2.getText() == theirMove && btnC2.getText() == theirMove){
			
			btnA2.setBackground(Color.red);
			btnB2.setBackground(Color.red);
			btnC2.setBackground(Color.red);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end C1,C2,C3 loss
	
		if (btnA3.getText() == theirMove && btnB3.getText() == theirMove && btnC3.getText() == theirMove){
		
			btnA3.setBackground(Color.red);
			btnB3.setBackground(Color.red);
			btnC3.setBackground(Color.red);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end C1,C2,C3 loss
	
		if (btnA1.getText() == theirMove && btnB2.getText() == theirMove && btnC3.getText() == theirMove){
			
			btnA1.setBackground(Color.red);
			btnB2.setBackground(Color.red);
			btnC3.setBackground(Color.red);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end C1,C2,C3 loss
	
		if (btnC1.getText() == theirMove && btnB2.getText() == theirMove && btnA3.getText() == theirMove){
		
			btnC1.setBackground(Color.red);
			btnB2.setBackground(Color.red);
			btnA3.setBackground(Color.red);
			btnA1.setEnabled(false);
			btnA2.setEnabled(false);
			btnA3.setEnabled(false);
			btnB1.setEnabled(false);
			btnB2.setEnabled(false);
			btnB3.setEnabled(false);
			btnC1.setEnabled(false);
			btnC2.setEnabled(false);
			btnC3.setEnabled(false);
			return true;
		}//end C1,C2,C3 loss
		//end loss conditions
		else{
			return false;
		}
		
	}
	/**
	 * Checks for a tie (all spaces used, no win).
	 * @return boolean value, returns true if tie found, else returns false.
	 */
	private boolean checkTie(){
		
		if(btnA1.getText() != null && btnA2.getText() != null && btnA3.getText() != null && btnB1.getText() != null && btnB2.getText() != null && btnB3.getText() != null &&btnC1.getText() != null && btnC2.getText() != null && btnC3.getText() != null &&
				!checkWinLose()){
			
			btnA1.setBackground(Color.yellow);
			btnA2.setBackground(Color.yellow);
			btnA3.setBackground(Color.yellow);
			btnB1.setBackground(Color.yellow);
			btnB2.setBackground(Color.yellow);
			btnB3.setBackground(Color.yellow);
			btnC1.setBackground(Color.yellow);
			btnC2.setBackground(Color.yellow);
			btnC3.setBackground(Color.yellow);
			return true;
		}
		
		else{
			
			return false;
			
		}
	}
	
	/**
	 * Launches a dialogue box informing the player if they won, lost, or tied and prompts them to play again. If yes, the game board is reset. If no, a message is sent informing their opponent that they have disconnected and the diconnect() method is called. 
	 * @param winLoseTie - a String message informing the player if they won, lost, or tied.
	 * @return boolean, if yes returns true, else returns false.
	 */
	private boolean newGame(String winLoseTie){
		
		int reply = JOptionPane.showConfirmDialog(null, winLoseTie, "New Game", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION){
			btnA1.setText(null);
			btnA2.setText(null);
			btnA3.setText(null);
			btnB1.setText(null);
			btnB2.setText(null);
			btnB3.setText(null);
			btnC1.setText(null);
			btnC2.setText(null);
			btnC3.setText(null);
			btnA1.setBackground(null);
			btnA2.setBackground(null);
			btnA3.setBackground(null);
			btnB1.setBackground(null);
			btnB2.setBackground(null);
			btnB3.setBackground(null);
			btnC1.setBackground(null);
			btnC2.setBackground(null);
			btnC3.setBackground(null);
			return true;
		}
		else{
			sendMessage(disconnectMsg);
			disconnect();
			JOptionPane.showMessageDialog(null, "You have disconnected from the server");
			return false;
		}
	}//end newGame
	
}//end class MainWindow
