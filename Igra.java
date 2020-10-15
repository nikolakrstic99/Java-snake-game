package drugiZadatak;

import java.awt.event.*;
import java.awt.*;

public class Igra extends Frame{
	private Mreza mreza;
	private Label labelaNovcici = new Label("Novcici"),
			labelaPoeni=new Label("Poeni: 0");
	private TextField zadatoNovicica = new TextField("");
	private Button pocni= new Button("Pocni");
	
	private CheckboxGroup cgroup = new CheckboxGroup();
	private Checkbox cTrava=new Checkbox("Trava",cgroup,true),
			cZid=new Checkbox("Zid",cgroup,false);
	
	private MenuItem izmena = new MenuItem("Rezim izmena"),
			igranja = new MenuItem("Rezim igranja");
	private boolean rIzmena=false,newIsGreen=true;;
	
	public Igra() {
		super("Igra");
		this.setBounds(100, 100, 600, 600);
		
		
		add(right(),BorderLayout.EAST);
		dodajMeni();
		
		
		
		add(mreza = new Mreza(this),BorderLayout.CENTER);
		add(down(),BorderLayout.SOUTH);
		setResizable(true);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				mreza.ugasi();
			}
		});
		
		cTrava.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				newIsGreen=true;
			}
		});
		
		cZid.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				newIsGreen=false;
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			mreza.ugasi();
			 dispose();
		}});
	}
	
	
	public void setMreza(Mreza m) {
		add(m,BorderLayout.CENTER);
		repaint();
	}
	
	private void dodajMeni() {
		MenuBar traka=new MenuBar();
		setMenuBar(traka);
		Menu meni = new Menu("Rezim");
		traka.add(meni);
		meni.add(izmena);
		meni.add(igranja);
		
		izmena.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(mreza.isActive()) {
						mreza.zaustavi();
					}
					zadatoNovicica.setEnabled(false);
					rIzmena=true;
					pocni.setEnabled(false);
				}
			}
		);
		
		igranja.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					zadatoNovicica.setEnabled(true);
					rIzmena=false;
					pocni.setEnabled(true);
				}
			}
		);
	}
	
	private Panel down() {
		Panel p= new Panel();
		
		p.add(labelaNovcici);
		p.add(zadatoNovicica);
		p.add(labelaPoeni);
		p.add(pocni);
		
		pocni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(mreza.pokrenuta())
					mreza.zaustavi();
				mreza.setBrNovcica(Integer.parseInt(zadatoNovicica.getText()));
				mreza.napraviIgraca();
				mreza.startuj();
			}
			
		});
		
		return p;
	}
	
	private Panel right() {
		Panel p = new Panel(new GridLayout(1,0));
		Label l=new Label("Podloga:");
		p.add(l);
		
		
		Panel travaZid=new Panel(new GridLayout(2,1)),
		trava = new Panel(new GridLayout(1,1)),zid = new Panel(new GridLayout(1,1));
		trava.add(cTrava);
		zid.add(cZid);
		travaZid.add(trava,Panel.CENTER_ALIGNMENT);
		travaZid.add(zid,Panel.CENTER_ALIGNMENT);
		trava.setBackground(Color.green);
		zid.setBackground(Color.lightGray);
		
		p.add(travaZid);
		return p;
	}
	
	public boolean getrIzmena() {
		return rIzmena;
	}
	
	public boolean getnewIsGreen() {
		return newIsGreen;
	}

	public void setPoeni(int i) {
		String s = "Poeni: " + i;
		labelaPoeni.setText(s);
	}
	
	public static void main(String[] args) {
		new Igra();
	}


	public void ponovo() {
		mreza.validate();
		mreza.repaint();
		mreza.setVisible(true);
	}
}
