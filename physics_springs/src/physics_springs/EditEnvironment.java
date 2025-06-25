package physics_springs;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class EditEnvironment extends JPanel {
	private static final long serialVersionUID = 1L; // so eclipse doesn't scream at me
	
	public JTextField gf;
	public JTextField bf;
	public JTextField vf;
	public JTextField tf;
	
	public EditEnvironment(double g, double b, Vector wind) {
		JPanel gp = new JPanel();
		gp.setLayout(new BoxLayout(gp, BoxLayout.X_AXIS));
		gp.add(new JLabel("g ="));
		gp.add(gf = new JTextField(Double.toString(g), 10));
		gp.add(new JLabel("cm/s²"));
		
		JPanel bp = new JPanel();
		bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
		bp.add(new JLabel("b ="));
		bp.add(bf = new JTextField(Double.toString(b), 10));
		bp.add(new JLabel("N·s/m"));
		
		JPanel vp = new JPanel();
		vp.setLayout(new BoxLayout(vp, BoxLayout.X_AXIS));
		vp.add(new JLabel("wind speed ="));
		vp.add(vf = new JTextField(Double.toString(wind.magnitude()), 10));
		vp.add(new JLabel("cm/s"));
		
		JPanel tp = new JPanel();
		tp.setLayout(new BoxLayout(tp, BoxLayout.X_AXIS));
		tp.add(new JLabel("wind angle ="));
		tp.add(tf = new JTextField(Double.toString(wind.angleDeg()), 10));
		tp.add(new JLabel("°"));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(gp);
		this.add(bp);
		this.add(vp);
		this.add(tp);
	}
	
	public static VarsEnvironment dialog(double g, double b, Vector wind) {
		EditEnvironment myPanel = new EditEnvironment(g, b, wind);
		int result = JOptionPane.showConfirmDialog(null, myPanel, "edit environmental variables", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			g = Double.parseDouble(myPanel.gf.getText());
			b = Double.parseDouble(myPanel.bf.getText());
			double windV = Double.parseDouble(myPanel.vf.getText());
			double windTheta = Math.toRadians(Double.parseDouble(myPanel.tf.getText()));
			wind = new Vector(windTheta).scale(windV);
		}
		return(new VarsEnvironment(g,b,wind));
	}
	
	public static void main(String[] args) {
		dialog(980, 0.1, new Vector(900,100));
		System.out.println(5 / 255.0);
	}
	
	
}
