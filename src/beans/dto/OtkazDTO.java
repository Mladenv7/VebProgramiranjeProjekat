package beans.dto;

import java.time.LocalDateTime;

public class OtkazDTO {
	private String idKarte, korisnickoIme;
	private LocalDateTime vremeOtkaza;

	public String getIdKarte() {
		return idKarte;
	}

	public void setIdKarte(String idKarte) {
		this.idKarte = idKarte;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public LocalDateTime getVremeOtkaza() {
		return vremeOtkaza;
	}

	public void setVremeOtkaza(LocalDateTime vremeOtkaza) {
		this.vremeOtkaza = vremeOtkaza;
	}
	
	
}
