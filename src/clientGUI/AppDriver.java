package clientGUI;

import javax.swing.JOptionPane;

import serverPac.ServerGUI;



/**
 * @author Daniel Lavigne (664978)
 * @version 1.0
 * Class Description: Main method to run the program.
 * Calls the Game GUI and the Server GUI.
 */
public class AppDriver {

	/**
	 * Main method for program
	 * @param args
	 */
	public static void main(String[] args) {
		
		int reply = JOptionPane.showConfirmDialog(null, "Welcome to Tic-Tac-Toe \n Do you wish to host the server?", "New Game", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION){
			
			
			ServerGUI sg = new ServerGUI();
			sg.setVisible(true);
			MainWindow mw = new MainWindow();
			mw.setVisible(true);
			
		}
		else{
			
			MainWindow mw = new MainWindow();
			mw.setVisible(true);
		}
		
	}

}
