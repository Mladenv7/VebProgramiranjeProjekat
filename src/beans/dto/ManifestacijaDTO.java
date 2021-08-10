package beans.dto;

import java.time.LocalDateTime;

public class ManifestacijaDTO {


   private String naziv,tip,poster,prodavac ;
   private int brojMesta;
   private double cenaRegular;
   private LocalDateTime vremeOdrzavanja;
   
public ManifestacijaDTO(String naziv, String tip, int brMesta, double cenaRegular, String poster,LocalDateTime vremeOdrzavanja) {
	super();
	this.naziv = naziv;
	this.tip = tip;
	this.brojMesta = brMesta;
	this.cenaRegular = cenaRegular;
	this.poster = poster;
	this.vremeOdrzavanja = vremeOdrzavanja;
}

public ManifestacijaDTO() {
	super();
}

public String getProdavac() {
	return prodavac;
}
public void setProdavac(String prodavac) {
	this.prodavac = prodavac;
}
public String getNaziv() {
	return naziv;
}
public void setNaziv(String naziv) {
	this.naziv = naziv;
}
public String getTip() {
	return tip;
}
public void setTip(String tip) {
	this.tip = tip;
}
public int getBrMesta() {
	return brojMesta;
}
public void setBrMesta(int brMesta) {
	this.brojMesta = brMesta;
}
public double getCenaRegular() {
	return cenaRegular;
}
public void setCenaRegular(double cenaRegular) {

	this.cenaRegular = cenaRegular;
}
public String getPoster() {
	return poster;
}
public void setPoster(String poster) {
	this.poster = poster;
}
public LocalDateTime getVremeOdrzavanja() {
	return vremeOdrzavanja;
}
public void setVremeOdrzavanja(LocalDateTime vremeOdrzavanja) {
	this.vremeOdrzavanja = vremeOdrzavanja;
}
    	
}
