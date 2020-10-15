package drugiZadatak;

import java.awt.*;
import java.util.*;

public class Mreza extends Panel implements Runnable {
	private int dimenzija;
	private Igra igra;
	private int osvojenoNovcica = 0;
	private Polje[][] matrica;
	private Igrac igrac;
	private Thread nit;
	private boolean radi = false;
	private java.util.List<Figura> novcici = new LinkedList<Figura>();

	private java.util.List<Figura> tenkovi = new LinkedList<Figura>();

	public Mreza(int dimenzija, Igra igra) {
		super();
		this.dimenzija = dimenzija;
		this.igra = igra;
		matrica = new Polje[dimenzija][dimenzija];
		this.setLayout(new GridLayout(dimenzija, dimenzija));
		popuniMatricu();
		igrac = null;

	}

	public void setBrNovcica(int n) {
		setNovciceItenkove(n);
	}

	public Mreza(Igra igra) {
		this(17, igra);
	}

	public Igra getIgra() {
		return igra;
	}

	public int getDimenzija() {
		return dimenzija;
	}

	public Figura getIgrac() {
		return igrac;
	}

	public Polje[][] getMatrica() {
		return matrica;
	}

	public java.util.List<Figura> getNovcici() {
		return novcici;
	}

	public java.util.List<Figura> getTenkovi() {
		return tenkovi;
	}

	public void setNovciceItenkove(int n) {
		Tenk t;
		for (int i = 0; i < n; i++) {
			int vrsta = (int) (Math.random() * 16);
			int kolona = (int) (Math.random() * 16);
			while (matrica[vrsta][kolona].getZauzeto() || matrica[vrsta][kolona] instanceof Zid) {
				vrsta = (int) (Math.random() * 16);
				kolona = (int) (Math.random() * 16);
			}
			matrica[vrsta][kolona].setZauzeto(true);
			novcici.add(new Novcic(matrica[vrsta][kolona]));

			if (i % 3 == 0) {
				vrsta = (int) (Math.random() * 16);
				kolona = (int) (Math.random() * 16);
				while (matrica[vrsta][kolona].getZauzeto() || matrica[vrsta][kolona] instanceof Zid) {
					vrsta = (int) (Math.random() * 16);
					kolona = (int) (Math.random() * 16);
				}
				matrica[vrsta][kolona].setZauzeto(true);
				t = new Tenk(matrica[vrsta][kolona]);
				t.startuj();
				tenkovi.add(t);
			}

		}
	}
	
	public void ugasi() {
		for(Figura f:tenkovi)
			((Tenk) f).ugasi();
		nit.interrupt();
	}
	
	public boolean pokrenuta() {
		return radi;
	}

	public synchronized void zaustavi() {

		for (Figura t : tenkovi)
			((Tenk) t).zaustavi();
		for (Figura t : tenkovi)
			t.polje.clear();
		for (Figura n : novcici)
			n.polje.clear();
		novcici.removeAll(novcici);
		tenkovi.removeAll(tenkovi);
		igrac.getPolje().clear();
		igrac = null;
		osvojenoNovcica = 0;
		radi = false;
	}

	public synchronized void startuj() {
		radi = true;
		if (nit != null)
			nit.interrupt();
		nit = new Thread(this);
		nit.start();
	}

	public boolean isActive() {
		return radi;
	}

	public void napraviIgraca() {
		int vrsta = (int) (Math.random() * dimenzija + 1);
		int kolona = (int) (Math.random() * dimenzija + 1);
		while (matrica[vrsta][kolona] instanceof Zid) {
			vrsta = (int) (Math.random() * 16);
			kolona = (int) (Math.random() * 16);
		}
		this.igrac = new Igrac(matrica[vrsta][kolona]);
		matrica[vrsta][kolona].setZauzeto(true);
	}

	private void popuniMatricu() {
		int br = 0;
		for (int i = 0; i < dimenzija; i++)
			for (int j = 0; j < dimenzija; j++) {
				br = (int) (Math.random() * 101); // brojevi od 0 do 100
				if (br < 80) {
					matrica[i][j] = new Trava(this);
					add(matrica[i][j]); // 80%
				} else {
					matrica[i][j] = new Zid(this);
					add(matrica[i][j]); // 20%
				}
			}
	}

	public void izmeni(int[] n) {

		int v = n[0];
		int k = n[1];
		remove(v * dimenzija + k);

		if (igra.getnewIsGreen())
			matrica[v][k] = new Trava(this);
		else
			matrica[v][k] = new Zid(this);

		add(matrica[v][k], v * dimenzija + k);
		validate();
		repaint();
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (!radi)
						wait();
				}
				Thread.sleep(40);
				azuriraj();
				azurirajLabele();
				repaint();

			}
		} catch (InterruptedException e) {

		}
	}

	private synchronized void azurirajLabele() {
		igra.setPoeni(osvojenoNovcica);
	}

	private synchronized void azuriraj() {

		for (Figura t : tenkovi)
			if (t.getPolje() == igrac.getPolje())
				zaustavi();

		boolean naso = false;
		int poz = -1;
		for (int i = 0; i < novcici.size(); i++) {
			if (novcici.get(i).getPolje() == igrac.getPolje()) {
				naso = true;
				poz = i;
			}
		}
		if (naso) {
			novcici.remove(poz);
			osvojenoNovcica++;
			if (novcici.size() == 0)
				zaustavi();
		}
	}

	@Override
	public void paint(Graphics g) {

		for (Figura n : novcici)
			n.crtaj();

		for (Figura t : tenkovi)
			t.crtaj();

		if (igrac != null)
			igrac.crtaj();
	}
}
