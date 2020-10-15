package drugiZadatak;

import java.awt.*;
import java.awt.event.*;

public abstract class Polje extends Canvas {
	protected Mreza mreza;
	private boolean zauzeto = false;

	public Mreza getMreza() {
		return mreza;
	}

	public Polje(Mreza mreza) {
		super();
		this.mreza = mreza;
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 0) {
					if (mreza.getIgra().getrIzmena()) 
						mreza.izmeni(pozicija());
				}
			}
		});

		addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				if (mreza.isActive()) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_W:
						if (mreza.getIgrac().getPolje().dohvPolje(-1, 0) != null) {
							if (mreza.getIgrac().getPolje().dohvPolje(-1, 0).dozvoljeno(mreza.getIgrac()) == false)
								break;

							mreza.getIgrac().getPolje().clear();
							mreza.getIgrac().pomeri(mreza.getIgrac().getPolje().dohvPolje(-1, 0));
						}
						break;
					case KeyEvent.VK_A:
						if (mreza.getIgrac().getPolje().dohvPolje(0, -1) != null) {
							if (mreza.getIgrac().getPolje().dohvPolje(0, -1).dozvoljeno(mreza.getIgrac()) == false)
								break;

							mreza.getIgrac().getPolje().clear();
							mreza.getIgrac().pomeri(mreza.getIgrac().getPolje().dohvPolje(0, -1));
						}
						break;
					case KeyEvent.VK_S:
						if (mreza.getIgrac().getPolje().dohvPolje(1, 0) != null) {
							if (mreza.getIgrac().getPolje().dohvPolje(1, 0).dozvoljeno(mreza.getIgrac()) == false)
								break;

							mreza.getIgrac().getPolje().clear();
							mreza.getIgrac().pomeri(mreza.getIgrac().getPolje().dohvPolje(1, 0));
						}
						break;
					case KeyEvent.VK_D:
						if (mreza.getIgrac().getPolje().dohvPolje(0, 1) != null) {
							if (mreza.getIgrac().getPolje().dohvPolje(0, 1).dozvoljeno(mreza.getIgrac()) == false)
								break;

							mreza.getIgrac().getPolje().clear();
							mreza.getIgrac().pomeri(mreza.getIgrac().getPolje().dohvPolje(0, 1));
						}
						break;
					}
				}
			}
		});
	}

	public boolean getZauzeto() {
		return zauzeto;
	}

	public void setZauzeto(boolean b) {
		zauzeto = b;
	}

	public int[] pozicija() {
		int[] n = new int[2];
		int dimenzija = mreza.getDimenzija();
		for (int i = 0; i < dimenzija; i++)
			for (int j = 0; j < dimenzija; j++) {
				if (this == mreza.getMatrica()[i][j]) {
					n[0] = i;
					n[1] = j;
					return n;
				}
			}
		return null;
	}

	public Polje dohvPolje(int v, int k) {
		int[] n = new int[2];
		int dim = mreza.getDimenzija();
		n = pozicija();

		if ((n[0] + v) >= dim || (n[0] + v) < 0 || (n[1] + k) >= dim || (n[1] + k) < 0)
			return null;
		return mreza.getMatrica()[n[0] + v][n[1] + k];
	}

	public abstract boolean dozvoljeno(Figura f);

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Polje other = (Polje) obj;
		if (mreza == null) {
			if (other.mreza != null)
				return false;
		} else if (!mreza.equals(other.mreza))
			return false;
		return true;
	}

	public synchronized void clear() {
		Graphics g = getGraphics();
		if (g != null) {
			Color color = g.getColor();
			g.setColor(color.green);
			g.clearRect(0, 0, getWidth(), getHeight());

			g.setColor(color);
		}
	}

}