package beans;

public class Komentar {
	private Korisnik autor;
	private Manifestacija manifestacija;
	private String tekst;
	private int ocena; //ocena ide od 1 do 5
	private boolean obrisan;
	
	public Korisnik getAutor() {
		return autor;
	}
	public void setAutor(Korisnik autor) {
		this.autor = autor;
	}
	public Manifestacija getM() {
		return manifestacija;
	}
	public void setM(Manifestacija manifestacija) {
		this.manifestacija = manifestacija;
	}
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	public int getOcena() {
		return ocena;
	}
	public void setOcena(int ocena) {
		this.ocena = ocena;
	}
	public Manifestacija getManifestacija() {
		return manifestacija;
	}
	public void setManifestacija(Manifestacija manifestacija) {
		this.manifestacija = manifestacija;
	}
	public boolean isObrisan() {
		return obrisan;
	}
	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
	public Komentar(Korisnik autor, Manifestacija manifestacija, String tekst, int ocena, boolean obrisan) {
		super();
		this.autor = autor;
		this.manifestacija = manifestacija;
		this.tekst = tekst;
		this.ocena = ocena;
		this.obrisan = obrisan;
	}
	
	public Komentar() {
		super();
		this.autor = null;
		this.manifestacija = null;
		this.tekst = "";
		this.ocena = 1;
		this.obrisan = true;
	}
}
