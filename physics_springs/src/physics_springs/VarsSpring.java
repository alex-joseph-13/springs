package physics_springs;

public final class VarsSpring {
	// this class is just a holder for data
	public double k;
	public double equilibriumLength;
	
	public VarsSpring(double K, double EL) {
		k = K;
		equilibriumLength = EL;
	}
	
	public VarsSpring(Physics.Spring s) {
		k = s.k;
		equilibriumLength = s.equilibriumLength;
	}
}
