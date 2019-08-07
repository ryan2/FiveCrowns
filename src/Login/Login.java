package Login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
		window.setSize(150,200);
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
				(new Thread(Ready)).start();
			}
		});
		JButton b2 = new JButton("Join");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//window.setVisible(false);
				//window.dispose();
				i=0;
				(new Thread(new ready())).start();
			}
		});
		panel.add(label);panel.add(b1);panel.add(b2);
		window.add(panel);
		window.setVisible(true);
	}
	
	public void updatePlayer(int count) {
		Ready.updatePlayer(count);
	}

	private class ready implements Runnable{

		private JFrame window;
		private JLabel label;
		private Server server;
		
		private void host() {
			server = new Server(login);
			(new Thread(server)).start();
			Client client = new Client();
			(new Thread(client)).start();
			try {
				Game game = new Game(server);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		private void join() {
			Client client = new Client();
			(new Thread(client)).start();
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
		
		@Override
		public void run() {
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
