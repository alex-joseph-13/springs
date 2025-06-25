package physics_springs;

public interface PhysicsObject {
	
	public Vector getPos();
	public void calcForces();
	public void update(double dt);
	public void display();
	public void delete();
}
