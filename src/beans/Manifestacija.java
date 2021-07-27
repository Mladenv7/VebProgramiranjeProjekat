package beans;

import java.time.LocalDateTime;

public class Manifestacija {
	
	 
	private String naziv ,tipManifestacije, id;
	private int brojMesta;
	private LocalDateTime vremeOdrzavanja;
	private double cenaRegular;
	private boolean status; //aktivno - true; neaktivno - false
	private Lokacija lokacija;
	private String poster;
	private boolean obrisana;
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public boolean isObrisana() {
		return obrisana;
	}
	public void setObrisana(boolean obrisana) {
		this.obrisana = obrisana;
	}
	public Manifestacija(String naziv, String tipManifestacije, int brojMesta, LocalDateTime vremeOdrzavanja,
			double cenaRegular, boolean status, Lokacija lokacija, String poster, boolean obrisana) {
		super();
		this.naziv = naziv;
		this.tipManifestacije = tipManifestacije;
		this.brojMesta = brojMesta;
		this.vremeOdrzavanja = vremeOdrzavanja;
		this.cenaRegular = cenaRegular;
		this.status = status;
		this.lokacija = lokacija;
		this.poster = poster;
		this.obrisana = obrisana;
	}
	
	public Manifestacija() {
		super();
		this.naziv = "";
		this.tipManifestacije = null;
		this.brojMesta = 0;
		this.vremeOdrzavanja = null;
		this.cenaRegular = 0;
		this.status = false;
		this.lokacija = null;
		this.poster = null;
		this.obrisana = true;
	}
	@Override
	public String toString() {
		return "Manifestacija [naziv=" + naziv + ", tipManifestacije=" + tipManifestacije + ", id=" + id
				+ ", brojMesta=" + brojMesta + ", vremeOdrzavanja=" + vremeOdrzavanja + ", cenaRegular=" + cenaRegular
				+ ", status=" + status + ", lokacija=" + lokacija + ", poster=" + poster + ", obrisana=" + obrisana
				+ "]";
	}
	
	
}
