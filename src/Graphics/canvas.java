package Graphics;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
//Colors Green Club, Red Heart, Black Spade, Yellow Star, Blue Diamond

class Surface extends JPanel implements ActionListener {

	Shape circle;
	private int i = 0;
    private final int DELAY = 150;
    private Timer timer;

    public Surface() {

        initTimer();
    }

    private void initTimer() {
    	System.out.println(DELAY);
        timer = new Timer(20, this);
        timer.start();
    }
    
    public Timer getTimer() {
        
        return timer;
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.blue);

        int w = getWidth();
        int h = getHeight();

        Random r = new Random();

        for (int i = 0; i < 2000; i++) {

            int x = Math.abs(r.nextInt()) % w;
            int y = Math.abs(r.nextInt()) % h;
            g2d.drawLine(x, y, x, y);
        }
    }
    
    public void drawStar(Graphics g,int mx) {
        int midX = mx;
        int midY = 300;
        int radius[] = {40,20,40,20};
        int nPoints = 12;
        int[] X = new int[nPoints];
        int[] Y = new int[nPoints];

        for (double current=0.0; current<nPoints; current++)
        {
            int i = (int) current;
            double x = Math.cos(current*((2*Math.PI)/12))*radius[i % 4];
            double y = Math.sin(current*((2*Math.PI)/12))*radius[i % 4];

            X[i] = (int) x+midX;
            Y[i] = (int) y+midY;
        }

        g.setColor(Color.yellow);
        g.fillPolygon(X, Y, nPoints);
    }
    
 // Draw a heart.
    public void drawHeart(Graphics g, int x, int y, int width, int height, Color c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(c);
    	int[] triangleX = {
                x - 2*width/18 +2,
                x + width + 2*width/18 -2,
                (x - 2*width/18 + x + width + 2*width/18)/2};
        int[] triangleY = { 
                y + height - 2*height/3, 
                y + height - 2*height/3, 
                y + height };
        g.fillOval(
                x - width/12,
                y, 
                width/2 + width/6, 
                height/2); 
        g.fillOval(
                x + width/2 - width/12,
                y,
                width/2 + width/6,
                height/2);
        g.fillPolygon(triangleX, triangleY, triangleX.length);
    }

    @Override
    public void paintComponent(Graphics g) {
    	i+=1;
    	if (i==400) {
    		i = -150;
    	}
        super.paintComponent(g);
        doDrawing(g);
        drawHeart(g,100+i,100,50,50, Color.RED);
        drawStar(g,125+i);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}

public class canvas extends JFrame {

    public canvas() {

        initUI();
    }

    private void initUI() {

        final Surface surface = new Surface();
        add(surface);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = surface.getTimer();
                timer.stop();
            }
        });

        setTitle("Points");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}