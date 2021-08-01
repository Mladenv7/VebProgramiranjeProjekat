package beans;

public class Komentar {
	private String autor;
	private String idManifestacije;
	private String tekst;
	private int ocena; 
	private boolean obrisan;
	
	
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIdManifestacije() {
		return idManifestacije;
	}

	public void setIdManifestacije(String idManifestacije) {
		this.idManifestacije = idManifestacije;
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

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	public Komentar(String autor, String idManifestacije, String tekst, int ocena, boolean obrisan) {
		super();
		this.autor = autor;
		this.idManifestacije = idManifestacije;
		this.tekst = tekst;
		this.ocena = ocena;
		this.obrisan = obrisan;
	}
	
	public Komentar() {
		super();
		this.autor = "";
		this.idManifestacije = "";
		this.tekst = "";
		this.ocena = 1;
		this.obrisan = true;
	}
}
