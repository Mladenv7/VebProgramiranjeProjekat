package beans;

public class Komentar {
	private String autor;
	private String idManifestacije;
	private String tekst;
	private int ocena; 
	private boolean obrisan, aktivan;
	
	
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
	
	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	public Komentar(String autor, String idManifestacije, String tekst, int ocena, boolean obrisan, boolean aktivan) {
		super();
		this.autor = autor;
		this.idManifestacije = idManifestacije;
		this.tekst = tekst;
		this.ocena = ocena;
		this.obrisan = obrisan;
		this.aktivan = aktivan;
	}
	
	public Komentar() {
		super();
		this.autor = "";
		this.idManifestacije = "";
		this.tekst = "";
		this.ocena = 1;
		this.obrisan = false;
		this.aktivan = false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		else {
			if(obj == null) return false;
			
			if (!(obj instanceof Komentar)) {
	            return false;
	        }
	        
			
			Komentar drugi = (Komentar) obj;
			
			if(this.autor.equals(drugi.getAutor()) && this.idManifestacije.equals(drugi.getIdManifestacije()) && this.tekst.equals(drugi.getTekst())
					&& this.ocena == drugi.getOcena() && this.obrisan == drugi.isObrisan() && this.aktivan == drugi.isAktivan()) return true;
			
			
			return false;
		}
	}
	
	
}
