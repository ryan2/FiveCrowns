package Graphics;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Window {
	
	JFrame window;
	
	public Window() {
		System.out.println("Setting window");
		window = new JFrame();
		window.setSize(640,480);
		window.setTitle("Five Crowns");
		setWindow();
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setWindow() {
		JPanel header = new JPanel(new FlowLayout());
		JLabel label = new JLabel("Your Turn");
		header.add(label);
		header.setBackground(Color.RED);
		JPanel panel = setPanel();
		JPanel canvas = setCanvas();
		window.setLayout(new BorderLayout());
		window.add(header, BorderLayout.NORTH);
		window.add(panel,BorderLayout.SOUTH);
		window.add(canvas,BorderLayout.CENTER);
	}

	private JPanel setCanvas() {
		JPanel panel = new JPanel(new FlowLayout());
		JLabel label = new JLabel("The Game");
		panel.add(label);
		panel.setBackground(Color.GREEN.darker());
		return panel;
	}
	
	private JPanel setPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		JButton response = new JButton("1");
		panel.add(response);
		window.add(panel);
		panel.setBackground(Color.LIGHT_GRAY);
		response.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				panel.setBackground(Color.CYAN);
			}
		});
		return panel;
	}
}
