package beans.dto;

import java.time.LocalDateTime;

public class ManifestUpitDTO {
	private String naziv, lokacija, sort; //lokacija je kombinacija ulice, njenog broja i postanskog broja
	private LocalDateTime datumOd, datumDo;
	private double cenaOd, cenaDo;
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getLokacija() {
		return lokacija;
	}
	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public LocalDateTime getDatumOd() {
		return datumOd;
	}
	public void setDatumOd(LocalDateTime datumOd) {
		this.datumOd = datumOd;
	}
	public LocalDateTime getDatumDo() {
		return datumDo;
	}
	public void setDatumDo(LocalDateTime datumDo) {
		this.datumDo = datumDo;
	}
	public double getCenaOd() {
		return cenaOd;
	}
	public void setCenaOd(double cenaOd) {
		this.cenaOd = cenaOd;
	}
	public double getCenaDo() {
		return cenaDo;
	}
	public void setCenaDo(double cenaDo) {
		this.cenaDo = cenaDo;
	}
	
	
}
