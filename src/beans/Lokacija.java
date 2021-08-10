package beans;

public class Lokacija implements Comparable<Lokacija>{
	
	private double geoSirina, geoDuzina;
	private String ulicaBroj, grad, postanskiBroj;
	private boolean obrisana;

	public String getUlicaBroj() {
		return ulicaBroj;
	}
	public void setUlicaBroj(String ulicaBroj) {
		this.ulicaBroj = ulicaBroj;
	}
	public String getGrad() {
		return grad;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}
	public String getPostanskiBroj() {
		return postanskiBroj;
	}
	public void setPostanskiBroj(String postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}
	public String getAdresa() {
		return this.ulicaBroj+" "+this.grad+" "+this.postanskiBroj;
	}
	public double getGeoSirina() {
		return geoSirina;
	}
	public void setGeoSirina(double geoSirina) {
		this.geoSirina = geoSirina;
	}
	public double getGeoDuzina() {
		return geoDuzina;
	}
	public void setGeoDuzina(double geoDuzina) {
		this.geoDuzina = geoDuzina;
	}
	public boolean isObrisana() {
		return obrisana;
	}
	public void setObrisana(boolean obrisana) {
		this.obrisana = obrisana;
	}
	public Lokacija(double geoSirina, double geoDuzina, String ulicaBroj, String grad, String postanskiBroj,
			boolean obrisana) {
		super();
		this.geoSirina = geoSirina;
		this.geoDuzina = geoDuzina;
		this.ulicaBroj = ulicaBroj;
		this.grad = grad;
		this.postanskiBroj = postanskiBroj;
		this.obrisana = obrisana;
	}
	public Lokacija() {
		super();
		this.geoSirina = 0;
		this.geoDuzina = 0;
		this.ulicaBroj = "";
		this.grad = "";
		this.postanskiBroj = "";
		this.obrisana = false;
	}
	
	public Lokacija(Lokacija l) {
		super();
		this.geoSirina = l.getGeoSirina();
		this.geoDuzina = l.getGeoDuzina();
		this.ulicaBroj = l.getUlicaBroj();
		this.grad = l.getGrad();
		this.postanskiBroj = l.getPostanskiBroj();
		this.obrisana = l.isObrisana();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {  
	          return true;  
	    }  
		
		if(obj instanceof Lokacija) {
			Lokacija druga = (Lokacija) obj;
			
			if(this.ulicaBroj.equalsIgnoreCase(druga.getUlicaBroj()) 
					&& this.grad.equalsIgnoreCase(druga.getGrad())
					&& this.postanskiBroj.equalsIgnoreCase(druga.getPostanskiBroj())
					&& this.geoSirina == druga.getGeoSirina() 
					&& this.geoDuzina == druga.getGeoDuzina()) 
				return true;
		}
	
		return false;
		
	}
	@Override
	public int compareTo(Lokacija o) {
		String adresa = this.ulicaBroj+" "+this.grad+" "+this.postanskiBroj;
		String adresa2 = o.getUlicaBroj()+" "+o.getGrad()+" "+o.getPostanskiBroj();
		return adresa.compareToIgnoreCase(adresa2);
	}
	
	
}
