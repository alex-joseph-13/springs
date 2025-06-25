package physics_springs;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditSpring extends JPanel {
	private static final long serialVersionUID = -5322564383837803798L;
	
	public JTextField kf;
	public JTextField lf;
	
	public EditSpring(Physics.Spring s) {
		JPanel kp = new JPanel();
		kp.setLayout(new BoxLayout(kp, BoxLayout.X_AXIS));
		kp.add(new JLabel("spring constant ="));
		kp.add(kf = new JTextField(Double.toString(s.k), 10));
		kp.add(new JLabel("N/m"));
		
		JPanel lp = new JPanel();
		lp.setLayout(new BoxLayout(lp, BoxLayout.X_AXIS));
		lp.add(new JLabel("equilibrium length ="));
		lp.add(lf = new JTextField(Double.toString(s.equilibriumLength), 10));
		lp.add(new JLabel("cm"));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(kp);
		this.add(lp);
	}
	
	public static VarsSpring dialog(Physics.Spring s) {
		EditSpring myPanel = new EditSpring(s);
		
		double k = s.k;
		double el = s.equilibriumLength;
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, "edit spring properties", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			k = Double.parseDouble(myPanel.kf.getText());
			el = Double.parseDouble(myPanel.lf.getText());
		}
		return(new VarsSpring(k,el));
	}
	
	
}
