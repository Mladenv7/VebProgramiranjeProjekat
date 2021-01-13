package beans;

public class Lokacija {
	
	private double geoSirina, geoDuzina;
	private String adresa;
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
	public Lokacija(double geoSirina, double geoDuzina, String adresa) {
		super();
		this.geoSirina = geoSirina;
		this.geoDuzina = geoDuzina;
		this.adresa = adresa;
	}
	
	public Lokacija() {
		super();
		this.geoSirina = 0;
		this.geoDuzina = 0;
		this.adresa = "";
	}
	
	public Lokacija(Lokacija l) {
		super();
		this.geoSirina = l.getGeoSirina();
		this.geoDuzina = l.getGeoDuzina();
		this.adresa = l.getAdresa();
	}
}
