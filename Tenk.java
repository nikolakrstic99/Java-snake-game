package drugiZadatak;

import java.awt.Color;
import java.awt.Graphics;

public class Tenk extends Figura implements Runnable {
	private Thread nit;
	private boolean radi;
	private Color c = Color.black;

	public enum smer {
		LEVO, DESNO, GORE, DOLE
	};

	public Tenk(Polje polje) {
		super(polje);
	}

	synchronized public void startuj() {
		radi = true;
		nit = new Thread(this);
		nit.start();
	}

	synchronized public void zaustavi() {
		radi = false;
	}
	
	public void ugasi() {
		nit.interrupt();
	}

	public synchronized void azurirajPolozaj() {
		int n = (int) (Math.random() * 4);
		if(polje!=null)
			polje.clear();
		switch (n) {
		case 0:
			if (getPolje().pozicija()[1] == 0) {
				azurirajPolozaj();
				break;
			} else {
				if (getPolje().dohvPolje(0, -1).dozvoljeno(this))
					pomeri(getPolje().dohvPolje(0, -1));
				else
					azurirajPolozaj();
			}
			break;
		case 1:
			if (getPolje().pozicija()[1] == (getPolje().getMreza().getDimenzija() - 1)) {
				azurirajPolozaj();
				break;
			} else {
				if (getPolje().dohvPolje(0, 1).dozvoljeno(this))
					pomeri(getPolje().dohvPolje(0, 1));
				else
					azurirajPolozaj();
			}
			break;
		case 2:
			if (getPolje().pozicija()[0] == 0) {
				azurirajPolozaj();
				break;
			} else {
				if (getPolje().dohvPolje(-1, 0).dozvoljeno(this))
					pomeri(getPolje().dohvPolje(-1, 0));
				else
					azurirajPolozaj();
			}
			break;
		case 3:
			if (getPolje().pozicija()[0] == (getPolje().getMreza().getDimenzija() - 1)) {
				azurirajPolozaj();
				break;
			} else {
				if (getPolje().dohvPolje(1, 0).dozvoljeno(this))
					pomeri(getPolje().dohvPolje(1, 0));
				else
					azurirajPolozaj();
			}
			break;
		}
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (!radi)
						wait();
				}
				if(polje!=null)
					azurirajPolozaj();
				Thread.sleep(500);

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void crtaj() {
		Graphics g = polje.getGraphics();
		Color color = g.getColor();
		g.setColor(c);
		int w = polje.getWidth();
		int h = polje.getHeight();
		g.drawLine(0, 0, w, h);
		g.drawLine(w, 0, 0, h);

		g.setColor(color);
	}
}
