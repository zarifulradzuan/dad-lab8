import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Sender {

	private JFrame frame;
	private JTextField txtStringToSend;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sender window = new Sender();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Sender() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblAddStringHere = new JLabel("Add string here");
		lblAddStringHere.setBounds(70, 35, 95, 14);
		frame.getContentPane().add(lblAddStringHere);
		
		txtStringToSend = new JTextField();
		txtStringToSend.setBounds(70, 56, 187, 20);
		frame.getContentPane().add(txtStringToSend);
		txtStringToSend.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBounds(70, 114, 187, 91);
		frame.getContentPane().add(textArea);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Runnable run = new Runnable(){
					@Override
					public void run(){
						try {
							Socket clientSocket = new Socket("localhost",8081);
							DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
							outToServer.writeUTF(txtStringToSend.getText());
							DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
							String fromServer = inFromServer.readUTF();
							textArea.setText(fromServer);
							clientSocket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						 
					}
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		btnSend.setBounds(267, 55, 89, 23);
		frame.getContentPane().add(btnSend);
		
		JLabel lblOutput = new JLabel("Output:");
		lblOutput.setBounds(70, 89, 46, 14);
		frame.getContentPane().add(lblOutput);
		

	}
}
