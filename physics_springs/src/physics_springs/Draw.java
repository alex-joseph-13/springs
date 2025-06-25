package physics_springs;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
//import java.awt.Graphics;
//import javax.swing.JFrame;
import javax.swing.JPanel;



public class Draw extends JPanel {
	private static final long serialVersionUID = 1851741672229567575L;
	
	protected double MAX_FRAMERATE = 60.0;
	private double MIN_TICK /*in nanoseconds*/ = 1_000_000_000.0 / MAX_FRAMERATE;
	private double lastTime = System.nanoTime();
	
	
	Graphics G = null;
	
	// drawing variables
	
	Color fillColor;
	boolean strokeOn;
	Color strokeColor;
	int fontSize;
	int totalWidth;
	int totalHeight;
	
	public Draw() {
		// initializer
		fillColor = Color.WHITE;
		strokeOn = true;
		strokeColor = Color.BLACK;
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				requestFocusInWindow();
				Draw.this.mousePressed(e.getX(), e.getY());
			}
		});
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Draw.this.keyPressed(e.getKeyChar());
			}
		});
	}
	
	
	
	
	
	
	
	
	
	
	
	

	// drawing methods
	
	
	
	
	
	void size(int w, int h) {
		setPreferredSize(new Dimension(w, h));
		totalWidth = w;
		totalHeight = h;
	}
	
	
	
	
	
	void rect(double x, double y, double w, double h) {
		// x,y specifies top left corner
		G.setColor(fillColor);
		G.fillRect((int)x, (int)y, (int)w, (int)h);
		if(strokeOn) {
			G.setColor(strokeColor);
			G.drawRect((int)x, (int)y, (int)w, (int)h);
		}
	}
	
	void rect(Vector pos, double w, double h) {
		// pos specifies top left corner
		rect(pos.x, pos.y, w, h);
		return;
	}
	
	void square(double x, double y, double s) {
		// x,y specifies center
		rect(x-s/2, y-s/2, s, s);
		return;
	}
	
	void square(Vector pos, double s) {
		// pos specifies center
		square(pos.x,  pos.y, s);
		return;
	}
	
	
	
	
	
	void ellipse(double x, double y, double w, double h) {
		// x,y specifies center
		G.setColor(fillColor);
		G.fillOval((int)(x-w/2), (int)(y-h/2), (int)w, (int)h);
		if(strokeOn) {
			G.setColor(strokeColor);
			G.drawOval((int)(x-w/2), (int)(y-h/2), (int)w, (int)h);
		}
	}
	
	void ellipse(Vector pos, double w, double h) {
		// pos specifies center
		ellipse(pos.x, pos.y, w, h);
		return;
	}
	
	void circle(double x, double y, double r) {
		// x,y specifies center
		ellipse(x, y, r, r);
		return;
	}
	
	void circle(Vector pos, double r) {
		// pos specifies center
		circle(pos.x, pos.y, r);
		return;
	}
	
	
	
	
	
	void line(double x1, double y1, double x2, double y2) {
		if(strokeOn) {
			G.setColor(strokeColor);
			G.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
		}
	}
	
	void line(Vector p1, Vector p2) {
		line(p1.x, p1.y, p2.x, p2.y);
	}
	
	
	
	
	
	void triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
		Polygon triangle = new Polygon(
				new int[] {(int)x1,(int)x2,(int)x3},
				new int[] {(int)y1,(int)y2,(int)y3},
				3);
		
		G.setColor(fillColor);
		G.fillPolygon(triangle);
		if(strokeOn) {
			G.setColor(strokeColor);
			G.drawPolygon(triangle);
		}
	}
	
	void trianlge(Vector p1, Vector p2, Vector p3) {
		triangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
	}
	
	
	
	

	void fill(color c) {
		fillColor = c.toAwtColor();
		return;
	}
	
	void fill(int r, int g, int b) {
		fill(new color(r,g,b));
		return;
	}
	
	void fill(int v) {
		fill(v, v, v);
		return;
	}
	
	
	
	
	
	void background(color c) {
		G.setColor( c.toAwtColor() );
		G.fillRect(0, 0, totalWidth, totalHeight);
		return;
	}
	
	void background(int r, int g, int b) {
		background(new color(r,g,b));
		return;
	}
	
	void background(int v) {
		background(v,v,v);
		return;
	}
	
	
	
	
	
	void stroke(color c) {
		strokeOn = true;
		strokeColor = c.toAwtColor();
	}
	
	void stroke(int r, int g, int b) {
		stroke( new color(r,g,b) );
		return;
	}
	
	void stroke(int v) {
		stroke(v, v, v);
		return;
	}
	
	
	
	
	
	void noStroke() {
		strokeOn = false;
		return;
	}
	
	
	
	
	
	void textSize(int s) {
		fontSize = s;
		G.setFont(getFont().deriveFont(s));
		return;
	}
	
	
	
	
	
	void text(String msg, double x, double y) {
		G.setColor(strokeColor);
		G.drawString(msg, (int)x, (int)y);
		return;
	}
	
	void text(String msg, Vector pos) {
		text(msg, pos.x, pos.y);
		return;
	}
	
	
	
	
	
	String prompt(String message) {
		return JOptionPane.showInputDialog(message);
	}
	
	String prompt(String message, Object hint) {
		return JOptionPane.showInputDialog(message, hint);
	}
	
	Double doublePrompt(String message, double hint) {
		return Double.parseDouble( JOptionPane.showInputDialog(message, hint) );
	}
	
	
	
	
	
	
	
	
	
	
	// paint component
	
	void draw() {}; // intentionally left blank
	
	public void paintComponent(Graphics G) {
		super.paintComponent(G);
		this.G = G;
		this.draw();
	}
	
	
	void setup() {
		size(400,400);
	}
	
	
	protected void start() {
		while(true) {
			if( System.nanoTime() - lastTime >= MIN_TICK ) {
				//System.out.println(1_000_000_000.0 / (System.nanoTime() - lastTime)); // framerate
				lastTime = System.nanoTime();
				repaint();
			}
		}
	}
	
	
	
	// event handling
	void keyPressed(char key) {};
	void mousePressed(double mouseX, double mouseY) {}; // intentionally left blank
	
	
	
	
	
	// main method
	
	
	public static void main(String[] args) {
		Draw U = new Draw();
		JFrame frame = new JFrame("Processing sketch");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(U);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.setVisible(true);
        
        U.start();
	}
	
}
