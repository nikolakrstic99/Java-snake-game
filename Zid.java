package drugiZadatak;

import java.awt.Color;

public class Zid extends Polje{
	
	Color c = Color.LIGHT_GRAY;
	
	public Zid(Mreza mreza) {
		super(mreza);
		this.setBackground(c);
	}	

	@Override
	public boolean dozvoljeno(Figura f) {
		return false;
	}
}