package serverPac;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
/**
 * 
 * @author Daniel Lavigne (664978)
 * Class Description: Generates a GUI for the server
 *
 */
public class ServerGUI extends JFrame implements Observer{

	private JPanel contentPane;
	JButton btnStart = new JButton();
	public ObjectOutputStream oos = null;
	private Thread t = null;
	private final JTextArea textArea = new JTextArea();

	/**
	 * Create the frame.
	 */
	public ServerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		btnStart.setText("Start Server");
		btnStart.addActionListener(new buttonListener());
		btnStart.setEnabled(true);
		contentPane.add(btnStart, BorderLayout.SOUTH);
		
		contentPane.add(textArea, BorderLayout.CENTER);
	}
	/**
	 * The main Action Listener method for the server window GUI
	 *Allows the user to start the server and displays messages sent by the server
	 *
	 */
	private class buttonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == btnStart){
				connect();
				textArea.append("Server Started \n");
			}//end btnStart
		}
	}
	/**
	 * Main update method for the observer.
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		GameServer gs = (GameServer)o;
		String msg = gs.getMsg();
		textArea.append(msg);
		
	}
	/**
	 * Method to start the server.
	 */
	private void connect(){
		btnStart.setEnabled(false);
		GameServer gs = new GameServer(this);
		t = new Thread(gs);
		t.start();
	}
	
	
}
