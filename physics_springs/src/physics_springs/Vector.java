package physics_springs;

public class Vector {
	double x;
	double y;
	
	public Vector(double X, double Y){
		this.x = X;
		this.y = Y;
	}
	public Vector(double theta){
		// theta is in radians
		this.x = Math.cos(theta);
		this.y = -Math.sin(theta);
	}
	
	public String toString(){
		return "<" + x + "," + y + ">";
	}
	
	//properties
	public double magnitude(){ return Math.sqrt(x*x + y*y); }
	public double mag2(){ return x*x + y*y; }
	public Vector normalized(){ return this.divide(this.magnitude()); }
	public double angle(){ return Math.atan2(-this.y, this.x); }
	public double angleDeg() { return Math.toDegrees(angle()); }
	
	//operators
	public Vector add(Vector that) { return new Vector(this.x + that.x, this.y + that.y); }
	public Vector subtract(Vector that) { return new Vector(this.x - that.x, this.y - that.y); }
	public Vector vectorTo(Vector that) { return that.subtract(this); }
	public Vector directionTo(Vector that) { return that.subtract(this).normalized(); }
	public double distanceTo(Vector that) { return that.subtract(this).magnitude(); }
	public Vector scale(double c) { return new Vector(this.x * c, this.y * c); }
	public Vector divide(double c) { return this.scale(1.0 / c); }
	public double dot(Vector that) { return this.x * that.x + this.y * that.y; }
	public boolean equals(Vector that) { return this.x==that.x & this.y==that.y; }
	
	//defaults
	public static final Vector ZERO = new Vector(0,0);
	
}
