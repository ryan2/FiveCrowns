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
	JTable scoreboard = new JTable();
	
	JLabel headerLabel = new JLabel();
	JLabel label = new JLabel();
	JLabel msg = new JLabel();
	JLabel footerLabel = new JLabel();
	JLabel Card = new JLabel();
	JLabel discard = new JLabel();

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
	JButton finishbutton;
	JButton scorebutton;
	
	public JTextField name = new JTextField(16);
	boolean turn;
	int player;
	int round;
	boolean finalTurn;
	boolean finish = false;
	boolean out;
	
	
	public Window(Client c) {
		client = c;
		window = new JFrame();
		window.setSize(640,480);
		window.setTitle("Five Crowns");
		setStartWindow();
		window.setVisible(true);
		round = 2;
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
		window.add(scoreboard,BorderLayout.EAST);
		canvas.add(Card, BorderLayout.CENTER);
		canvas.add(discard,BorderLayout.LINE_START);
		
	}
	
	public void Reset() {
		msg.setText("Try Again");
		if (finish) {
			setFinish();
		}
		else {
			doTurn();
		}
	}
	
	public void setPlayer(int i) {
		player = i;
		msg.setText(Integer.toString(i)+" Players - "+name.getText());
		panel.removeAll();
		panel.repaint();
		
	}
	
	private void setHeaderWait(){
		headerLabel.setText("Other Player's Turn");
		header.setBackground(Color.RED);
		header.add(headerLabel);
		
	}
	
	public void finalTurn() {
		finalTurn = true;
	}
	
	public void deal(String card) {
		String text = Card.getText();
		Card.setText(text+card);
		//canvas.add(Card, BorderLayout.CENTER);
		Card.setHorizontalAlignment(JLabel.CENTER);
		//canvas.repaint();
	}
	
	public void showDiscard(String card) {
		discard.setText("Discard Pile: "+card);
	}

	private void setName() {
		panel.removeAll();
		panel.add(name); 
		submitbutton = new JButton();
		submitbutton.setText("Submit");
		submitbutton.addActionListener(new namebutton());
		panel.add(submitbutton);
		}
	
	public void setHeaderTurn(){
		if (finalTurn) {
			headerLabel.setText("Someone's out! Final Turn");
			header.setBackground(Color.ORANGE);
		}
		else {
			headerLabel.setText("Your Turn");
			header.setBackground(Color.MAGENTA);	
			msg.setText("Your Turn");
		}
	}
	
	public void clearCards() {
		Card.setText("");
		discard.setText("");
		panel.removeAll();
		finalTurn = false;
		round+=1;
		out = false;
	}
	
	public void draw(String s) {
		String temp = Card.getText();
		Card.setText(temp+s);
		doDiscard();
	}
	
	public void setOut() {
		msg.setText("You are out! Last turn for the remaining players");
		panel.removeAll();
		header.removeAll();
		headerLabel.setText("Out! 0 Points Added");
		header.setBackground(Color.PINK);
		header.add(headerLabel);
		window.repaint();
		out = true;
	}
	
	public void setScore(String score) {
		msg.setText("Your score for this round: "+score+" Last turn for the remaining players");
		panel.removeAll();
		header.removeAll();
		headerLabel.setText(score+" Points Added");
		header.setBackground(Color.PINK);
		header.add(headerLabel);
		window.repaint();
	}
	
	public void doTurn() {
		panel.removeAll();
		drawbutton = new JButton();
		discardbutton = new JButton();
		outbutton = new JButton();
		
		drawbutton.setText("Draw Deck");
		discardbutton.setText("Draw Discard");
		outbutton.setText("Go Out");
		
		drawbutton.addActionListener(new drawbutton());
		discardbutton.addActionListener(new discardbutton());
		outbutton.addActionListener(new outbutton());
		
		panel.add(drawbutton);
		panel.add(discardbutton);
		panel.add(outbutton);
		window.setVisible(true);		
	}
	
	private void doOut(){
		msg.setText("Select the cards for your book or run");
		panel.removeAll();
		for (int i = 1;i<=round;i++) {
			int num =i-1;
			JButton card = new JButton(Integer.toString(i));
			card.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					System.out.println("Selected Card" + num);
					client.sendMessageVoid(String.valueOf(num));
					card.setEnabled(false);
					card.setBackground(Color.BLUE);
				}
			});
			panel.add(card);
	}
		submitbutton = new JButton();
		submitbutton.setText("Submit");
		submitbutton.addActionListener(new submitbutton());
		panel.add(submitbutton);
		panel.repaint();
		window.setVisible(true);
		System.out.println("DOING OUT");
	}
	
	private void doScore() {
		msg.setText("Select the cards for your book or run");
		panel.removeAll();
		for (int i = 1;i<=round;i++) {
			int num =i-1;
			JButton card = new JButton(Integer.toString(i));
			card.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					System.out.println("Selected Card" + num);
					client.sendMessageVoid(String.valueOf(num));
					card.setEnabled(false);
					card.setBackground(Color.BLUE);
				}
			});
			panel.add(card);
	}
		submitbutton = new JButton();
		submitbutton.setText("Submit");
		submitbutton.addActionListener(new submitbutton());
		panel.add(submitbutton);
		donebutton = new JButton();
		donebutton.setText("Done");
		donebutton.addActionListener(new donebutton());
		panel.add(submitbutton);
		panel.add(donebutton);
		panel.repaint();
		window.setVisible(true);
		System.out.println("DOING Score");
	}
	
	private void doDiscard() {
		msg.setText("Pick the number of the card you wish to discard.");
		panel.removeAll();
		window.setVisible(true);
		for (int i = 1;i<=round+1;i++) {
			int num =i-1;
			JButton card = new JButton(Integer.toString(i));
			card.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					System.out.println("Selected Card" + num);
					client.sendMessageVoid(String.valueOf(num));
					String cards = Card.getText();
					int start = 0,end = 0;
					int counter = num;
					for (int i = 0;i<cards.length();i++) {
						if (counter == 0) {
							start = i;
							if (cards.substring(i+1,i+2).matches("\\D")) {
								end = i+2;
							}
							else {
								end = i+3;
							}
							break;
						}
						if (cards.substring(i,i+1).matches("\\D")) {
							counter-=1;
						}
					}
					discard.setText("Discard Pile: "+cards.substring(start, end));
					Card.setText(cards.substring(0,start)+cards.substring(end));
					setFinish();
				}
			});
			panel.add(card);
			
		}
		window.setVisible(true);
	}
	
	public void setFinish() {
		finish = true;
		panel.removeAll();
		outbutton = new JButton();
		outbutton.setText("Go Out");
		panel.add(outbutton);
		outbutton.addActionListener(new outbutton());
		if (finalTurn) {
			scorebutton = new JButton();
			scorebutton.setText("Score");
			scorebutton.addActionListener(new scorebutton());
			panel.add(scorebutton);
		}
		else {
			finishbutton = new JButton();
			finishbutton.setText("Finish");
			finishbutton.addActionListener(new finishbutton());
			panel.add(finishbutton);
		}
		panel.repaint();
		window.setVisible(true);
	}
	
	private void setHeaderStart() {
		headerLabel.setText("Welcome to Five Crowns");
		header.setBackground(Color.ORANGE);
		header.add(headerLabel);
	}
	
	private void setCanvas() {
		label.setText("The Arena");
		label.setHorizontalAlignment(JLabel.CENTER);
		canvas.setSize(640,400);
		canvas.add(label,BorderLayout.PAGE_START);
		canvas.setBackground(Color.GREEN.darker());
		msg.setText("Enter");
		msg.setHorizontalAlignment(JLabel.CENTER);
		canvas.add(msg,BorderLayout.PAGE_END);
	}
	
	private void setPanelStart() {
		readybutton = new JButton();
		readybutton.setText("Ready");
		readybutton.addActionListener(new readybutton());
		panel.add(readybutton);
	}
	
	public void setHeaderNotTurn(String n){
		if (!out) {
			headerLabel.setText(n+"'s Turn");
			header.setBackground(Color.RED);
			msg.setText("Waiting for your turn");
			System.out.println("SET HEADER NOT TURN");
		}
	}

	
	class drawbutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				client.sendMessageVoid("Draw");
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
				client.sendMessageVoid("Done");
				for (Component button : panel.getComponents()) {
					if (button.isEnabled()==true||button.getBackground()==Color.blue){
						if (!(button.equals(donebutton)||button.equals(submitbutton))){
							((AbstractButton) button).doClick();
						}
					}
}
		  }
	}
	
	class discardbutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				client.sendMessageVoid("Discard");
				discard.setText("Discard Pile:");
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
				client.sendMessageVoid("Out");
				doOut();
		  }
	}  
	
	class submitbutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				client.sendMessageVoid("Submit");
				for (Component button : panel.getComponents()) {
					if (button.getBackground()==Color.blue){
						button.setBackground(null);
					}
				}
		  }
	}  
	
	class namebutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				client.sendMessageVoid("Name:"+name.getText());
				msg.setText("Waiting for Players");
				panel.removeAll();
				panel.repaint();
		  }
	}  
	
	class readybutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				client.sendMessageVoid("Ready");
				msg.setText("Please enter your Player Name (20 characters max)");
				setName();
		  }
	}  
	class scorebutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				client.sendMessageVoid("Score");
				doScore();
		  }
	}  
	class finishbutton implements ActionListener { 
		  public void actionPerformed(ActionEvent e) {
				client.sendMessageVoid("Finish");
				msg.setText("Waiting for your turn");
				finish= false;
				panel.removeAll();
		  }
	}  
		  
	
	
}	
	

