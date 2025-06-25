package physics_springs;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditBox extends JPanel {
	private static final long serialVersionUID = -6060648313346930158L;
	
	public JTextField mf;
	public JTextField sf;
	public JTextField hf;
	
	public EditBox(Physics.PointMass m) {
		JPanel mp = new JPanel();
		mp.setLayout(new BoxLayout(mp, BoxLayout.X_AXIS));
		mp.add(new JLabel("mass ="));
		mp.add(mf = new JTextField(Double.toString(m.mass), 10));
		mp.add(new JLabel("kg"));
		
		JPanel sp = new JPanel();
		sp.setLayout(new BoxLayout(sp, BoxLayout.X_AXIS));
		sp.add(new JLabel("size ="));
		sp.add(sf = new JTextField(Double.toString(m.size), 10));
		sp.add(new JLabel("pixels"));
		
		JPanel hp = new JPanel();
		hp.setLayout(new BoxLayout(hp, BoxLayout.X_AXIS));
		hp.add(new JLabel("color = #"));
		hp.add(hf = new JTextField( m.hue.toString(), 10));
		
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(mp);
		this.add(sp);
		this.add(hp);
	}
	
	public static VarsBox dialog(Physics.PointMass m) {
		EditBox myPanel = new EditBox(m);
		
		double mass = m.mass;
		double size = m.size;
		color hue = m.hue;
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, "edit box properties", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			mass = Double.parseDouble(myPanel.mf.getText());
			size = Double.parseDouble(myPanel.sf.getText());
			hue = new color(myPanel.hf.getText());
		}
		return(new VarsBox(mass, size, hue));
	}
	
	
}