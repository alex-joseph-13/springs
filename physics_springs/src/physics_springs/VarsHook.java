package physics_springs;

public final class VarsHook {
	// this class is just a holder for data
	public double mass;
	public double size;
	public color hue;
	
	public VarsHook(double S, color H) {
		size = S;
		hue = H;
	}
	
	public VarsHook(Physics.PointObject o) {
		size = o.size;
		hue = o.hue;
	}
}