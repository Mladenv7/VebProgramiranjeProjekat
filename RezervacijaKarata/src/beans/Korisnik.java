package beans;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Pol;
import enums.TipKupca;
import enums.Uloga;

public class Korisnik {
	private String korisnickoIme, lozinka, ime,prezime;
	private Pol pol;
	private LocalDate datumRodjenja;
	private Uloga uloga;
	private ArrayList<Karta> sveKarte;
	private ArrayList<Manifestacija> sveManifest;
	private int brBodova;
	private TipKupca tip;
	private boolean obrisan;
	
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public Pol getPol() {
		return pol;
	}
	public void setPol(Pol pol) {
		this.pol = pol;
	}
	public LocalDate getDatumRodjenja() {
		return datumRodjenja;
	}
	public void setDatumRodjenja(LocalDate datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}
	public Uloga getUloga() {
		return uloga;
	}
	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}
	public ArrayList<Karta> getSveKarte() {
		return sveKarte;
	}
	public void setSveKarte(ArrayList<Karta> sveKarte) {
		this.sveKarte = sveKarte;
	}
	public ArrayList<Manifestacija> getSveManifest() {
		return sveManifest;
	}
	public void setSveManifest(ArrayList<Manifestacija> sveManifest) {
		this.sveManifest = sveManifest;
	}
	public int getBrBodova() {
		return brBodova;
	}
	public void setBrBodova(int brBodova) {
		this.brBodova = brBodova;
	}
	public TipKupca getTip() {
		return tip;
	}
	public void setTip(TipKupca tip) {
		this.tip = tip;
	}
	public boolean isObrisan() {
		return obrisan;
	}
	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
	public Korisnik(String korisnickoIme, String lozinka, String ime, String prezime, Pol pol, LocalDate datumRodjenja,
			Uloga uloga, ArrayList<Karta> sveKarte,
			ArrayList<Manifestacija> sveManifest, int brBodova, TipKupca tip, boolean obrisan) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.datumRodjenja = datumRodjenja;
		this.uloga = uloga;
		this.sveKarte = sveKarte;
		this.sveManifest = sveManifest;
		this.brBodova = brBodova;
		this.tip = tip;
		this.obrisan = obrisan;
	}
	
	public Korisnik() {
		super();
		this.korisnickoIme = "prazanKorisnik";
		this.lozinka = "";
		this.ime = "";
		this.prezime = "";
		this.pol = null;
		this.datumRodjenja = null;
		this.uloga = null;
		this.sveKarte = null;
		this.sveManifest = null;
		this.brBodova = 0;
		this.tip = null;
		this.obrisan = false;
	}
	
	public Korisnik(Korisnik k) {
		super();
		this.korisnickoIme = k.getKorisnickoIme();
		this.lozinka = k.getLozinka();
		this.ime = k.getIme();
		this.prezime = k.getPrezime();
		this.pol = k.getPol();
		this.datumRodjenja = k.getDatumRodjenja();
		this.uloga = k.getUloga();
		this.sveKarte = k.getSveKarte();
		this.sveManifest = k.getSveManifest();
		this.brBodova = k.getBrBodova();
		this.tip = k.getTip();
		this.obrisan = k.isObrisan();
	}
}
