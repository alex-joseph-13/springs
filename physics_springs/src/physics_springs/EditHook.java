package physics_springs;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditHook extends JPanel {
	private static final long serialVersionUID = 9076843230971275610L;
	
	public JTextField sf;
	public JTextField hf;
	
	public EditHook(Physics.PointObject o) {
		
		JPanel sp = new JPanel();
		sp.setLayout(new BoxLayout(sp, BoxLayout.X_AXIS));
		sp.add(new JLabel("size ="));
		sp.add(sf = new JTextField(Double.toString(o.size), 10));
		sp.add(new JLabel("pixels"));
		
		JPanel hp = new JPanel();
		hp.setLayout(new BoxLayout(hp, BoxLayout.X_AXIS));
		hp.add(new JLabel("color = #"));
		hp.add(hf = new JTextField( o.hue.toString(), 10));
		
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(sp);
		this.add(hp);
	}
	
	public static VarsHook dialog(Physics.PointObject o) {
		EditHook myPanel = new EditHook(o);
		
		double size = o.size;
		color hue = o.hue;
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, "edit box properties", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			size = Double.parseDouble(myPanel.sf.getText());
			hue = new color(myPanel.hf.getText());
		}
		return(new VarsHook(size, hue));
	}
	
	
}