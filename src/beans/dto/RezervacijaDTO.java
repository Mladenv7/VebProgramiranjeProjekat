package beans.dto;

import java.time.LocalDateTime;

public class RezervacijaDTO {
	private String imePrezime, manifestacijaId, korisnickoIme; 
	private double cenaRegular;
	private LocalDateTime vremeOdrzavanja;
	private int[] kolicine;
	public String getImePrezime() {
		return imePrezime;
	}
	public void setImePrezime(String imePrezime) {
		this.imePrezime = imePrezime;
	}
	public String getManifestacijaId() {
		return manifestacijaId;
	}
	public void setManifestacijaId(String manifestacijaId) {
		this.manifestacijaId = manifestacijaId;
	}
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public double getCenaRegular() {
		return cenaRegular;
	}
	public void setCenaRegular(double cenaRegular) {
		this.cenaRegular = cenaRegular;
	}
	public LocalDateTime getVremeOdrzavanja() {
		return vremeOdrzavanja;
	}
	public void setVremeOdrzavanja(LocalDateTime vremeOdrzavanja) {
		this.vremeOdrzavanja = vremeOdrzavanja;
	}
	public int[] getKolicine() {
		return kolicine;
	}
	public void setKolicine(int[] kolicine) {
		this.kolicine = kolicine;
	}
	
	
}
