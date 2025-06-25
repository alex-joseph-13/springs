package physics_springs;

public final class VarsBox {
	// this class is just a holder for data
	public double mass;
	public double size;
	public color hue;
	
	public VarsBox(double M, double S, color H) {
		mass = M;
		size = S;
		hue = H;
	}
	
	public VarsBox(Physics.PointMass m) {
		mass = m.mass;
		size = m.size;
		hue = m.hue;
	}
}
