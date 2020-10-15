package drugiZadatak;

import java.awt.Color;
import java.awt.Graphics;

public class Trava extends Polje{
	private Color c = Color.green;

	public Trava(Mreza mreza) {
		super(mreza);
		this.setBackground(c);
	}

	@Override
	public boolean dozvoljeno(Figura f) {
		return true;
	}
}
