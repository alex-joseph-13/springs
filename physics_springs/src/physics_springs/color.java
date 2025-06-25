package physics_springs;

import java.awt.Color;

public final class color {
	int r;
	int g;
	int b;
	
	public color(int R,int G,int B) {
		r=R; g=G; b=B;
	}
	
	public color(int V) {
		r=V; g=V; b=V;
	}
	
	public color(String hexCode) {
		String rs = hexCode.substring(0,2);
		String gs = hexCode.substring(2,4);
		String bs = hexCode.substring(4,6);
		
		r = Integer.parseInt(rs,16);
		g = Integer.parseInt(gs,16);
		b = Integer.parseInt(bs,16);
	}
	
	
	
	public Color toAwtColor() {
		return new Color( (float)(r/255.0), (float)(g/255.0), (float)(b/255.0) );
	}
	
	public String toString() {
		return String.format("%02X", r) + String.format("%02X", g) + String.format("%02X", b);
	}
	

	static final color BLACK = new color(0);
	static final color DARK_GRAY = new color(64);
	static final color GRAY = new color(128);
	static final color LIGHT_GRAY = new color(192);
	static final color WHITE = new color(255);
}
