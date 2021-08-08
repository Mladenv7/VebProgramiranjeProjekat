package beans.dto;

import java.time.LocalDateTime;

public class KarteUpitDTO {
	private String manifestacija, sort, korisnickoIme;
	private double cenaOd, cenaDo;
	private LocalDateTime datumOd, datumDo;
	public String getManifestacija() {
		return manifestacija;
	}
	public void setManifestacija(String manifestacija) {
		this.manifestacija = manifestacija;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
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
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	@Override
	public String toString() {
		return "KarteUpitDTO [manifestacija=" + manifestacija + ", sort=" + sort + ", korisnickoIme=" + korisnickoIme
				+ ", cenaOd=" + cenaOd + ", cenaDo=" + cenaDo + ", datumOd=" + datumOd + ", datumDo=" + datumDo + "]";
	}
	
	
}
