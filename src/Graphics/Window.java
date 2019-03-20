/*Standard Turn: Deck, Discard, Out
 * Standard Discard: # of Cards
 * Out: Cards, Submit, Reset
 * Scoring: Cards, Submit, Reset, Done
 * 
 * 
 * 
 */
package Graphics;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Network.Client;
import javax.swing.*;

public class Window {
	
	Client client;
	
	JFrame window;
	
	JPanel header = new JPanel();
	JPanel panel = new JPanel();
	JPanel canvas = new JPanel(new BorderLayout());
	
	JLabel headerLabel = new JLabel();
	JLabel label = new JLabel();
	JLabel msg = new JLabel();

	JButton yesbutton;
	JButton nobutton;
	JButton resetbutton;
	JButton donebutton;
	JButton drawbutton;
	JButton discardbutton;
	JButton shufflebutton;
	JButton cutbutton;
	JButton outbutton;
	JButton submitbutton;
	JButton readybutton;
	
	JTextField name = new JTextField(16);
	int turn;
	
	
	public Window(Client c) {
		client = c;
		window = new JFrame();
		window.setSize(640,480);
		window.setTitle("Five Crowns");
		setStartWindow();
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	


	private void setStartWindow() {
		setHeaderStart();
		setPanelStart();
		setCanvas();
		window.setLayout(new BorderLayout());
		window.add(header, BorderLayout.NORTH);
		window.add(panel,BorderLayout.SOUTH);
		window.add(canvas,BorderLayout.CENTER);
		
	}
	
	private void setHeaderWait(){
		headerLabel.setText("Other Player's Turn");
		header.setBackground(Color.RED);
		header.add(headerLabel);
		
	}

	private void setName() {
		panel.removeAll();
		panel.add(name); 
		submitbutton = new JButton();
		submitbutton.setText("Submit");
		submitbutton.addActionListener(new namebutton());
		panel.add(submitbutton);
		}
	
	private void setHeaderTurn(){
		headerLabel.setText("Your Turn");
		header.setBackground(Color.MAGENTA);
		header.add(headerLabel);
		
	}
	
	private void setHeaderStart() {
		headerLabel.setText("Welcome to Five Crowns");
		header.setBackground(Color.MAGENTA);
		header.add(headerLabel);
	}

	
	private void setCanvas() {
		label.setText("The Arena");
		label.setHorizontalAlignment(JLabel.CENTER);
		canvas.setSize(640,400);
		canvas.add(label,BorderLayout.NORTH);
		canvas.setBackground(Color.GREEN.darker());
		msg.setText("Waiting for number of players");
		msg.setHorizontalAlignment(JLabel.CENTER);
		canvas.add(msg,BorderLayout.SOUTH);
	}
	
	private void setPanelStart() {
		readybutton = new JButton();
		readybutton.setText("Ready");
		readybutton.addActionListener(new readybutton());
		panel.add(readybutton);
	}
	
	private void setPanelCards(int cards) {
		JButton response;
		for (int i = 1;i<=cards;i++) {
			response = new JButton(Integer.toString(i));
			int num = i;
			response.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					System.out.println("Selected Card" + num);
					setHeaderWait();
					try {
						client.sendMessage(Integer.toString(num));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			panel.add(response);
		}
		window.add(panel);
		panel.setBackground(Color.LIGHT_GRAY);
	}
	
	class drawbutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				try {
					client.sendMessage("Draw");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }
	}
	
	class yesbutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				try {
					client.sendMessage("Yes");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }
	}
	
	class nobutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				try {
					client.sendMessage("No");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }
		  
	}
	
	class resetbutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				try {
					client.sendMessage("Reset");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }
	}  
	
	class donebutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				try {
					client.sendMessage("Done");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }
	}
	
	class discardbutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				try {
					client.sendMessage("Discard");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }
	}  
	
	class shufflebutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				try {
					client.sendMessage("Shuffle");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }
	}  
	
	class cutbutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				try {
					client.sendMessage("Cut");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }
	}  
	
	class outbutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				try {
					client.sendMessage("Out");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }
	}  
	
	class submitbutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				try {
					client.sendMessage("Submit");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }
	}  
	
	class namebutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				try {
					client.sendMessage("Name:"+name.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }
	}  
	
	class readybutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				try {
					msg.setText(client.sendMessage("Ready")+" Player(s). Please enter your Player Name (20 characters max)");
					setName();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }
	}  
		  
	
	
}	
	

