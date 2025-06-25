package physics_springs;

public final class VarsEnvironment {
	// this class is just a holder for data
	public double g;
	public double b;
	public Vector wind;
	
	public VarsEnvironment(double G, double B, Vector WIND) {
		g = G;
		b = B;
		wind = WIND;
	}
	public VarsEnvironment(double G, double B, double V, double THETA) {
		g = G;
		b = B;
		wind = new Vector(THETA).scale(V);
	}
}
