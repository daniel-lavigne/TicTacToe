/**
 * 
 */
package messagePac;

import java.io.Serializable;

/**
 * @author Daniel Lavigne (664978)
 *
 */
public class Message implements Serializable{
	
	private static final long serialVersionUID = 484092457682613595L;
	private String message;
	private String move;
	private String username;
	private int playerNum;
	private boolean turn;
	/**
	 * Default constructor for the Message object. 
	 */
	public Message(){
		
	}
	/**
	 * Main constructor for the Message object.
	 * @param playerNum - an integer assigned to a player determining whether they are player 1 or 2.
	 * @param username - the user name set by the player.
	 * @param message - a chat or disconnect message sent by the player.
	 * @param turn - a boolean value used to determine which player has the first move upon connecting to another player.
	 * @param move - a String value representing a specific move on the game board.
	 */
	public Message(int playerNum, String username, String message, boolean turn, String move){
		
		this.playerNum = playerNum;
		this.username = username;
		this.message = message;
		this.turn = turn;
		this.move = move;
		
	}
/**
 * Returns the user message
 * @return message - a String value composed by either the ClientHandler or the player
 */
	public String getMessage() {
		return message;
	}
/**
 * Sets the message
 * @param message - a String value composed by either the ClientHandler or the player
 */
	public void setMessage(String message) {
		this.message = message;
	}
/**
 * Returns the move made by the player
 * @return move - a String value representing a move on the game board
 */
	public String getMove() {
		return move;
	}
/**
 * Sets the move
 * @param move - a String value representing a move on the game board
 */
	public void setMove(String move) {
		this.move = move;
	}
/**
 * Returns the username
 * @return username - the username selected by the player
 */
	public String getUsername() {
		return username;
	}
/**
 * sets the username
 * @param username - the username selected by the player
 */
	public void setUsername(String username) {
		this.username = username;
	}
/**
 * Returns the player number set by the ClientHandler
 * @return playerNum - a number assigned to each player
 */
	public int getPlayerNum() {
		return playerNum;
	}
/**
 * Sets the playerNumber
 * @param playerNum - a number assigned to each player
 */
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}
/**
 * Returns a boolean value for turn
 * @return turn - a boolean used to determine first move
 */
	public boolean isTurn() {
		return turn;
	}
/**
 * Sets the value for turn
 * @param turn - a boolean used to determine first move
 */
	public void setTurn(boolean turn) {
		this.turn = turn;
	}
/**
 * Return a String representation for the Massage object
 * @return a String representation for the Massage object
 */
	@Override
	public String toString() {
		return username + ":\n" + message;
	}


}
