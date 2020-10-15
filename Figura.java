package drugiZadatak;


public abstract class Figura{
	protected Polje polje;

	public Polje getPolje() {
		return polje;
	}

	public Figura(Polje polje) {
		super();
		this.polje = polje;
	}
	
	public void pomeri(Polje p) {
		if(p!=null) {
			polje=p;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Figura other = (Figura) obj;
		if (polje == null) {
			if (other.polje != null)
				return false;
		} else if (!polje.equals(other.polje))
			return false;
		return true;
	}
	
	public abstract void crtaj();
			
	
}
