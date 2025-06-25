package physics_springs;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


public class Physics extends Draw {
	
	// so that eclipse doesn't yell at me
	private static final long serialVersionUID = 3753716327016557945L;
	
	
	ArrayList<PhysicsObject> world = new ArrayList<PhysicsObject>();
	
	JMenuBar menuBar;
	
	
	
	
	
	
	
	
	
	
	
	// can be used for fixed points
	
	class PointObject implements PhysicsObject {
		final boolean mobile = false;
		Vector position;
		Vector velocity;
		double size;
		color hue;
		
		public PointObject(double X, double Y) {
			this.position = new Vector(X,Y);
			this.velocity = Vector.ZERO;
			this.size = 20;
			this.hue = new color(255);
			world.add(this);
		}
		public PointObject(Vector pos){
			this.position = pos;
			this.velocity = Vector.ZERO;
			this.size = 20;
			this.hue = new color(255);
			world.add(this);
		}
		
		public Vector getPos(){
			return position;
		}
		
		public void calcForces(){}
		public void applyForce(Vector f){}
		//undefined behavior because there is no mass
	
		public void update(double dt) {
			position = position.add(velocity.scale(dt));
		}
		
		public void display() {
			fill(hue);
			circle(position, size/2);
		}
		
		public void delete(){
			world.remove(this);
			
			//find all springs that contain this object
			ArrayList<Spring> badSprings = new ArrayList<Spring>();
			for(PhysicsObject i: world){
				if(! (i instanceof Spring)) {continue;}
				
				Spring s = (Spring) i;
				if(s.contains(this)){
					badSprings.add(s);
				}
				
			}
			for(Spring s: badSprings){
				world.remove(s); // a separate loop para avoid concurrent modification
			}
			
			// now all references have been removed and the garbage collector kicks in 
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// point masses

	class PointMass extends PointObject {
		final boolean mobile = true;
		double mass;
		Vector acceleration;
		Vector netForce;
		
		public PointMass(Vector pos){
			super(pos);
			this.mass = 1;
			this.netForce = Vector.ZERO;
		}
		public PointMass(Vector pos, double Mass) {
			super(pos);
			this.mass = Mass;
			this.netForce = Vector.ZERO;
		}
		public PointMass(double X, double Y){
			super(X,Y);
			this.mass = 1;
			this.netForce = Vector.ZERO;
		}
		public PointMass(double X, double Y, double Mass) {
			super(X,Y);
			this.mass = Mass;
			this.netForce = Vector.ZERO;
		}
		
		public void calcForces(){
			Vector gravity = new Vector(0, mass * g);
			Vector airResistance = (velocity.subtract(wind)).scale(-b);
			
			netForce = gravity.add(airResistance);
		}
		
		public void applyForce(Vector force){
			netForce = netForce.add(force);
		}
		
		public void update(double dt){
			acceleration = netForce.divide(mass);
			velocity = velocity.add(acceleration.scale(dt));
			position = position.add(velocity.scale(dt));
			this.netForce = Vector.ZERO;
		}
		
		public void display(){
			fill(hue);
			square(position, size);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	class Spring implements PhysicsObject {
		PointObject e1;
		PointObject e2;
		double k;
		double equilibriumLength;
		
		public Spring(PointObject one, PointObject two, double K, double eqLength){
			this.e1 = one;
			this.e2 = two;
			this.k = K;
			this.equilibriumLength = eqLength;
			world.add(this);
		}
		public Spring(PointObject one, PointObject two, double K){
			this.e1 = one;
			this.e2 = two;
			this.k = K;
			this.equilibriumLength = e1.position.distanceTo(e2.position);
			world.add(this);
		}
		
		private Vector springVector(){
			return e1.position.vectorTo(e2.position);
		}
		private Vector springDirection(){
			return springVector().normalized();
		}
		private double currentLength(){
			return springVector().magnitude();
		}
		
		public Vector getPos(){
			return e1.getPos().add(e2.getPos()).divide(2);
		}
		public boolean contains(PointObject e){
			return e==e1 | e==e2;
		}
		
		public void calcForces(){
			double springForceMag = (currentLength() - equilibriumLength) * k;
			e1.applyForce( springDirection().scale(springForceMag) );
			e2.applyForce( springDirection().scale(-springForceMag) );
		}
		
		public void update(double dt){}
		
		public void display(){
			line(e1.position, e2.position);
		}
		
		public void delete(){
			world.remove(this);
			// now all references have been removed and the garbage collector kicks in
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// finder methods
	
	PhysicsObject findObject(double X, double Y){
		PhysicsObject closest = null;
		double minDistance = Double.MAX_VALUE;
		
		Vector clickSpot = new Vector(X,Y);
		
		for(PhysicsObject i: world){
			double distance = clickSpot.distanceTo(i.getPos());
			if(distance < minDistance){
				closest = i;
				minDistance = distance;
			}
		}
		
		return closest;
	}
	
	
	
	PointObject findPoint(double X, double Y){
		PointObject closest = null;
		double minDistance = Double.MAX_VALUE;
		
		Vector clickSpot = new Vector(X,Y);
		
		for(PhysicsObject i: world){
			if(! (i instanceof PointObject) ){continue;}
			
			double distance = clickSpot.distanceTo(i.getPos());
			if(distance < minDistance){
				closest = (PointObject) i;
				minDistance = distance;
			}
		}
		
		return closest;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Physics() {
		super();
		
		menuBar = new JMenuBar();
		
		JMenu mDraw = new JMenu("Draw");
		menuBar.add(mDraw);
		
			
		mDraw.add(new JMenuItem(
				new drawModeAction('m', "Point Mass",
				"Click to create a point mass (box)")));
		mDraw.add(new JMenuItem(
				new drawModeAction('r', "Fixed Point",
				"Click to create a fixed point (hook)")));
		mDraw.add(new JMenuItem(
				new drawModeAction('k', "Spring",
				"Click two points to create a spring between them")));
		
		
		JMenu mEdit = new JMenu("Edit");
		mEdit.getAccessibleContext().setAccessibleDescription(
				"Edit objects already in the simulation");
		menuBar.add(mEdit);
		
		mEdit.add(new JMenuItem(
				new drawModeAction('d', "Delete",
				"Click an object to delete it")));
		mEdit.add(new JMenuItem(
				new drawModeAction('e', "Edit Object",
				"Click an object to edit its properties")));
		mEdit.add(new JMenuItem(
				new settingsAction()));
		
		
	}
	
	
	
	class drawModeAction extends AbstractAction{
		private static final long serialVersionUID = 5281584685781914423L;
		//^ so eclipse doesn't yell at me
		
		char mode;
		public drawModeAction(char mode, String text, String desc) {
			super(text);
			putValue(SHORT_DESCRIPTION, desc);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(mode));
			this.mode = mode;
		}
		public void actionPerformed(ActionEvent e) {
			paused = true;
			Physics.this.drawMode = mode;
		}
	}
	
	class settingsAction extends drawModeAction{
		private static final long serialVersionUID = 5281584685781914423L;
		//^ so eclipse doesn't yell at me
		
		public settingsAction() {
			super('s', "Environmental Settings", "Edit the environmental variables");
		}
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			VarsEnvironment newVars = EditEnvironment.dialog(g, b, wind);
			g = newVars.g;
			b = newVars.b;
			wind = newVars.wind;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// control variables
	double timeLast = System.nanoTime();
	double dt = 1.0/60.0;
	
	// constants
	double g = 980;
	double b = 0.1;
	Vector wind = new Vector(100, 140);
	
	// visuals
	Vector motes = Vector.ZERO;
	
	// pause variables
	boolean paused = false;
	char drawMode = 'r';
	PhysicsObject focus = null;
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("unused")
	//^ physics objects are automatically added to an arraylist, so they are never unused
	void setup() {
		super.setup();
		
		PointObject springTop = new PointObject(200,50);
		PointMass mass1 = new PointMass(new Vector(200,200),0.7);
		Spring s1 = new Spring(springTop, mass1, 10);
		PointMass mass2 = new PointMass(new Vector(300,200),1.4);
		Spring s2 = new Spring(mass1, mass2, 10);
		PointObject p2 = new PointObject(350,50);
		Spring s3 = new Spring(p2, mass2, 10, 50);
		
		
		mass1.hue = new color(255,192,192);
		//mass2.hue = color(255,0,0);
	}
	
	
	
	
	void draw() {
		background(255);
		
		if(!paused) {
			for(PhysicsObject p: world){
				p.calcForces();
			}
			for(PhysicsObject p: world){
				p.update(dt);
			}
			
			
			// particle effects
			if(! (wind.equals(Vector.ZERO) || b==0) ){
				motes = motes.add(wind.scale(dt));
				for(int i=0;i<5;i++){		for(int j=0;j<5;j++){
					circle((float)(motes.x%100+100*i), (float)(motes.y%100+100*j), 1);
				} }
			}
			
		}
		
		stroke(0);
		for(PhysicsObject p: world){
			p.display();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	void keyPressed(char key){
	
		if(key == ' '){
			// space is pressed
			paused = !paused;
		} /*else if(paused){
			if(key == 'm'){
				drawMode = 'm';
			} else if(key == 'r'){
				drawMode = 'r';
			} else if(key == 'k'){
				drawMode = 'k';
			} else if(key == 'd'){
				drawMode = 'd';
			} else if(key == 'e'){
				drawMode = 'e';
			} else if(key == 's'){
				drawMode = 's';
			}
		} */
	}
	
	@Override
	void mousePressed(double mouseX, double mouseY){		
		
		if(!paused){
			return;
		}
		
		if(drawMode=='m'){
			new PointMass(mouseX, mouseY);
		} else if(drawMode=='r'){
			new PointObject(mouseX, mouseY);
		} else if(drawMode=='d'){
			PhysicsObject p = findObject(mouseX,mouseY);
			if(p != null){
				p.delete();
			}
		} else if(drawMode=='k'){
			focus = findPoint(mouseX,mouseY);
			if(focus != null) {
				drawMode = 'K';
			}
		} else if(drawMode=='K'){
			assert focus instanceof PointObject;
			PointObject springE1 = (PointObject) focus;
			new Spring(springE1, findPoint(mouseX,mouseY), 10.0);
			drawMode = 'k';
		} else if(drawMode=='s'){
			
			VarsEnvironment newVars = EditEnvironment.dialog(g, b, wind);
			g = newVars.g;
			b = newVars.b;
			wind = newVars.wind;
			
		} else if(drawMode=='e'){
			PhysicsObject p = findObject(mouseX, mouseY);
			
			if(p instanceof Spring){
				// spring edit menu
				Spring s = (Spring) p;
				VarsSpring newVars = EditSpring.dialog(s);
				s.k = newVars.k;
				s.equilibriumLength = newVars.equilibriumLength;
			} else if (p instanceof PointMass) {
				// box edit menu
				PointMass m = (PointMass) p;
				VarsBox newVars = EditBox.dialog(m);
				m.mass = newVars.mass;
				m.size = newVars.size;
				m.hue = newVars.hue;
			} else if (p instanceof PointObject) {
				// hook edit menu
				PointObject o = (PointObject) p;
				assert !o.mobile;
				VarsHook newVars = EditHook.dialog(o);
				o.size = newVars.size;
				o.hue = newVars.hue;
			}
			
		}
			
		
		
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
		Physics U = new Physics();
		U.setup();
		JFrame frame = new JFrame("Physics Springs");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(U);
        frame.setJMenuBar(U.menuBar);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.setVisible(true);
        
        U.start();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
