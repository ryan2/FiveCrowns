package Login;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.*;

import Game.Game;
import Network.Client;
import Network.Server;

public class Login{
	
	private JFrame window;
	private int i = 0; //host - 1; join - 0;
	ready Ready;
	private Login login = this;
	
	
	public Login() {
	}
	
	public void enter() {
		setWindow();
	}
	
	private void setWindow() {
		window = new JFrame("Portal");
		window.setSize(500,500);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Host or Join?");
		JButton b1 = new JButton("Host");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//window.setVisible(false);
				//window.dispose();
				i = 1;
				Ready = new ready();
				Ready.run1();
			}
		});
		JButton b2 = new JButton("Join");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//window.setVisible(false);
				//window.dispose();
				i=0;
				ready tmp = new ready();
				tmp.run1();
			}
		});
		panel.add(label);panel.add(b1);panel.add(b2);
		window.add(panel);
		window.setVisible(true);
	}
	
	public void updatePlayer(int count) {
		Ready.updatePlayer(count);
	}

	private class ready{

		private JFrame window;
		private JLabel label;
		private Server server;
		private String ip;
		
		private void host() {
			server = new Server(login);
			(new Thread(server)).start();
			Client client = new Client();
			try {
				client.run1(InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Game game = new Game(server);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		private void join() {
			Client client = new Client();
			window = new JFrame("Portal");
			window.setSize(500,500);
			window.setLocationRelativeTo(null);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel panel = new JPanel();
			label = new JLabel("Enter IP Address");
			JTextField textfield= new JTextField();
			textfield.setPreferredSize(new Dimension(200,30));
			JButton b1 = new JButton("Enter");
			b1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ip = textfield.getText();
					if (ip.isEmpty()) {
						label.setText("Could not connect, try a different address");
						label.repaint();
					}
					else {
					joinIP(client, ip);
					}
				}
			});
			panel.add(label);panel.add(textfield);panel.add(b1);
			window.add(panel);
			window.setVisible(true);
		}
		
		private void joinIP(Client client, String ip) {
			try {
				client.run1(ip);
				window.dispose();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				label.setText("Could not connect, try a different address");
				label.repaint();
				e1.printStackTrace();
			}
		}
		
		public void updatePlayer(int count) {
			label.setText("Start when ready. "+count+" Players");
			label.repaint();
			window.repaint();
			window.setVisible(true);
		}
		
		private void setWindow() {
				window = new JFrame("Portal");
				window.setSize(200,200);
				window.setLocationRelativeTo(null);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JPanel panel = new JPanel();
				label = new JLabel("Start when ready. 1 Player");
				JButton b1 = new JButton("Start");
				b1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						window.setVisible(false);
						window.dispose();
						server.hostStart();
					}
				});
				panel.add(label);panel.add(b1);
				window.add(panel);
				window.setVisible(true);
			
		}
		

		public void run1() {
			// TODO Auto-generated method stub
			if (i==1) {
				setWindow();
				host();
			}
			else {
				join();
			}
			}
		
		}
		
	}
