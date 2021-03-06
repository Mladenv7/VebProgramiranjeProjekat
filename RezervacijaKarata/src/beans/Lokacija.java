package beans;

public class Lokacija {
	
	private double geoSirina, geoDuzina;
	private String adresa;
	private boolean obrisana;
	
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
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public boolean isObrisana() {
		return obrisana;
	}
	public void setObrisana(boolean obrisana) {
		this.obrisana = obrisana;
	}
	public Lokacija(double geoSirina, double geoDuzina, String adresa, boolean obrisana) {
		super();
		this.geoSirina = geoSirina;
		this.geoDuzina = geoDuzina;
		this.adresa = adresa;
		this.obrisana = obrisana;
	}
	
	public Lokacija() {
		super();
		this.geoSirina = 0;
		this.geoDuzina = 0;
		this.adresa = "";
		this.obrisana = true;
	}
	
	public Lokacija(Lokacija l) {
		super();
		this.geoSirina = l.getGeoSirina();
		this.geoDuzina = l.getGeoDuzina();
		this.adresa = l.getAdresa();
		this.obrisana = l.isObrisana();
	}
}
