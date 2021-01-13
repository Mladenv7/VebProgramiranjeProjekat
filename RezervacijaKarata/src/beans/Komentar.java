package beans;

public class Komentar {
	private Korisnik autor;
	private Manifestacija manifestacija;
	private String tekst;
	private int ocena; //ocena ide od 1 do 5
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
	public Komentar(Korisnik autor, Manifestacija manifestacija, String tekst, int ocena) {
		super();
		this.autor = autor;
		this.manifestacija = manifestacija;
		this.tekst = tekst;
		this.ocena = ocena;
	}
	
	public Komentar() {
		super();
		this.autor = null;
		this.manifestacija = null;
		this.tekst = "";
		this.ocena = 1;
	}
}
