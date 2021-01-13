package beans;

import java.time.LocalDateTime;

public class Manifestacija {
	
	 
	private String naziv ,tipManifestacije;
	private int brojMesta;
	private LocalDateTime vremeOdrzavanja;
	private double cenaRegular;
	private boolean status; //aktivno - true; neaktivno - false
	private Lokacija lokacija;
	private String poster;
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getTipManifestacije() {
		return tipManifestacije;
	}
	public void setTipManifestacije(String tipManifestacije) {
		this.tipManifestacije = tipManifestacije;
	}
	public int getBrojMesta() {
		return brojMesta;
	}
	public void setBrojMesta(int brojMesta) {
		this.brojMesta = brojMesta;
	}
	public LocalDateTime getVremeOdrzavanja() {
		return vremeOdrzavanja;
	}
	public void setVremeOdrzavanja(LocalDateTime vremeOdrzavanja) {
		this.vremeOdrzavanja = vremeOdrzavanja;
	}
	public double getCenaRegular() {
		return cenaRegular;
	}
	public void setCenaRegular(double cenaRegular) {
		this.cenaRegular = cenaRegular;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Lokacija getLokacija() {
		return lokacija;
	}
	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	
}
