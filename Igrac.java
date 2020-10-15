package drugiZadatak;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends Figura{	
	Color c=Color.red;
	public Igrac(Polje polje) {
		super(polje);
	}
	

	@Override
	public void crtaj() {
		Graphics g= polje.getGraphics();
		Color color = g.getColor();
		g.setColor(c);
		int w=polje.getHeight();
		int h=polje.getHeight();
		
		g.setColor(c);
		
		g.drawLine(w/2, 0, w/2, h);
		g.drawLine(0, h/2, w, h/2);
		
		g.setColor(color);
	}
}
