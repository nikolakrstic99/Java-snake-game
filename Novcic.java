package drugiZadatak;

import java.awt.Color;
import java.awt.Graphics;

public class Novcic extends Figura{
	private Color c = Color.yellow;
	
	public Novcic(Polje polje) {
		super(polje);
	}

	@Override
	public void crtaj() {
		int w = polje.getWidth();
		int h = polje.getHeight();
		Graphics g = polje.getGraphics();
		Color color = g.getColor();
		
		g.setColor(c); 
		g.fillOval(w/4, h/4, w/2, h/2);
		
		g.setColor(color);
	}
	
}
